package com.space.aikq.design.proxy.aspectj;

/**
 *  Aspect 切面
 * @author aikaiqiang
 * @date 2019年05月31日 14:34
 */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAspect {

	/**
	 * @Pointcut 将 pointCut()方法定义为切点，切点中指定了切入的方法。
	 * execution表示在执行的时候，拦截（）中的方法；第一个* 表示任意返回类型的方法
	 * com.example.demo.service.impl.UserServiceImpl指定目标对象，.printUser(..)指定方法
	 * (..)表示任何参数都可以匹配
	 *
	 * 1）表示匹配所有方法
	 *     execution(* *(..))
	 * 2）表示匹配com.savage.server.UserService中所有的公有方法
	 *     execution(public * com. savage.service.UserService.*(..))
	 * 3）表示匹配com.savage.server包及其子包下的所有方法
	 *     execution(* com.savage.server..*.*(..))
	 */

	/**
	 *  指定 TargetInterface 接口和TargetObject下所有的公有方法
	 */
	@Pointcut("execution(public * com.space.aikq.design.proxy.jdk.TargetInterface.*(..))")
	public void pointCut() {

	}


	/**
	 *  @Before、@After、@AfterReturning、@AfterThrowing
	 * 指定在目标方法printUser(..)执行前后或返回异常等状态下将执行的自定义方法。
	 */
	@Before("pointCut()")
	public void before() {
		System.out.println("before........");
	}
	@After("pointCut()")
	public void after() {
		System.out.println("after.....");
	}
	@AfterReturning("pointCut()")
	public void afterReturning() {
		System.out.println("afterReturning......");
	}
	@AfterThrowing("pointCut()")
	public void afterThrowing() {
		System.out.println("afterRunning....");
	}

	/**
	 *  告知spring这是一个我们需要的bean
	 */
	//	@Bean(name = "myAspect")
	//	public MyAspect initMyAspect() {
	//		return new MyAspect();
	//	}


}
