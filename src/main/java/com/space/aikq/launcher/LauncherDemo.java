package com.space.aikq.launcher;

import sun.misc.Launcher;

import java.net.URL;
import java.util.ServiceLoader;

/**
 *  Launcher 喷射器：sun.misc.Launcher类是java的入口，
 *  在启动java应用的时候会首先创建Launcher类，创建Launcher类的时候回准备应用程序运行中需要的类加载器
 * @author aikaiqiang
 * @date 2019年06月27日 13:24
 */
public class LauncherDemo {

	public static void main(String[] args) {
		ClassLoader classLoader = Launcher.class.getClassLoader();
		if(classLoader == null) {
			System.out.println("classLoader1 is null");
		}
		System.out.println("=============================================================");

		ClassLoader appClassLoader = Launcher.getLauncher().getClassLoader();
		if(appClassLoader == null) {
			System.out.println("appClassLoader is null");
		}else {
			System.out.println("appClassLoader is not null");
			System.out.println("appClassLoader class : " + appClassLoader.getClass());
		}
		System.out.println("=============================================================");

		ClassLoader extClassLoader = appClassLoader.getParent();
		if(extClassLoader == null) {
			System.out.println("extClassLoader is null");
		}else {
			System.out.println("extClassLoader is not null");
			System.out.println("extClassLoader class : " + extClassLoader.getClass());
		}
		System.out.println("=============================================================");

		classLoadShow();
	}

	/**
	 * 在使用SPI的时候，ServiceLoader是通过BootStrap类加载器加载的，
	 * 在执行到加载用户编写的扩展类的时候，如果使用当前类的类加载器，是肯定无法加载到用户编写的类的，
	 * 这个时候就无法继续执行了，所以这个时候就需要使用Thread的上下文类加载器
	 * @param service
	 * @param <S>
	 * @return
	 */
	public static <S> ServiceLoader<S> load(Class<S> service) {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		return ServiceLoader.load(service, cl);
	}


	/**
	 * 1. Launcher源码里定义了static的扩展类加载器ExtClassLoader， static的系统类加载器AppClassLoader。
	 * 它们都是默认包级别的，它们都是继承URLClassLoader，这就意味着我们的代码里，不能定义ExtClassLoader laoder = ...或AppClassLoader loader = ...;我们只能ClassLoader loader = ...，而在实际运行时，我们应当能辨别这个loader到底是哪个具体类型;
	 * 2.在ExtClassLoader构造器里，并没有指定parent，或者说ExtClassLoader的parent为null。因为ExtClassLoader的parent是BootstrapLoader，而BootstrapLoader不存在于Java Api里，只存在于JVM里，我们是看不到的，所以请正确理解"ExtClassLoader的parent为null"的含义;
	 * 3.在AppClassLoader构造器里，有了parent。实例化AppClassLoader的时候，传入的parent就是一个ExtClassLoader实例;
	 * 4.看看Launcher的构造方法:
	 * 4-1.先实例化ExtClassLoader，从java.ext.dirs系统变量里获得URL[]
	 * 4-2.用这个ExtClassLoader作为parent去实例化AppClassLoader，从java.class.path系统变量里获得URL[]; Launcher getClassLoader()就是返回的这个AppClassLoader;
	 * 4-3.设置AppClassLoader为ContextClassLoader;
	 */
	public static void classLoadShow(){

		ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
		ClassLoader extClassloader = appClassLoader.getParent();
		ClassLoader bootstrapLoader  = extClassloader.getParent();
		System.out.println("the bootstrapLoader : " + bootstrapLoader);
		System.out.println("the extClassloader : " + extClassloader);
		System.out.println("the appClassLoader : " + appClassLoader);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("bootstrapLoader加载以下文件：");
		URL[] urls = Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i]);
		}

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("extClassloader加载以下文件：");
		System.out.println(System.getProperty("java.ext.dirs"));

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("appClassLoader加载以下文件：");
		System.out.println(System.getProperty("java.class.path"));

	}

}
