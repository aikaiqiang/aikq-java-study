package com.space.aikq.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 *  Zookeeper 客户端
 * @author aikq
 * @date 2018年11月30日 17:12
 */
public class ZkClient {

	ZooKeeper zooKeeper = null;

	public  void init() throws IOException {
		//创建一个与服务器的连接 需要(服务端的 ip+端口号)(session过期时间)(Watcher监听注册)
		zooKeeper = new ZooKeeper("47.106.108.22:2181", 2000, new Watcher() {
			@Override
			public void process(WatchedEvent watchedEvent) {
				// 监控所有被触发的事件
				System.out.println("已经触发了" + watchedEvent.getType() + "事件！");
			}
		});
	}

	/**
	 * 创建节点
	 * PERSISTENT(0, false, false), (持续的，相对于EPHEMERAL，不会随着client的断开而消失)
	 * PERSISTENT_SEQUENTIAL(2, false, true),（持久的且带顺序的）
	 * EPHEMERAL(1, true, false),(短暂的，生命周期依赖于client session)
	 * EPHEMERAL_SEQUENTIAL(3, true, true); (短暂的，带顺序的)
	 * @param nodeName
	 * @throws Exception
	 */
	public void creatNode(String nodeName) throws Exception{
		zooKeeper.create("/" + nodeName, nodeName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	/**
	 * 打印子节点
	 * @param nodePath
	 * @throws Exception
	 */
	public  void getChildren(String nodePath) throws Exception{
		List<String> childrens = zooKeeper.getChildren(nodePath, true);
		childrens.forEach(path -> {
			System.out.println(path);
		});
	}

	public static void main(String[] args) {
		try{
//			ZooKeeper zk = new ZooKeeper("47.106.108.22:2181", 2000, new Watcher() {
//				@Override
//				public void process(WatchedEvent watchedEvent) {
//					// 监控所有被触发的事件
//					System.out.println("已经触发了" + watchedEvent.getType() + "事件！");
//				}
//			});
//			zk.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//
//			// 创建一个子目录节点
//			zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//			System.out.println(new String(zk.getData("/testRootPath",false,null)));
//
//			// 取出子目录节点列表
//			System.out.println(zk.getChildren("/testRootPath",true));
//
//			// 创建另外一个子目录节点
//			zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//			System.out.println(zk.getChildren("/testRootPath",true));
//
//			// 修改子目录节点数据
//			zk.setData("/testRootPath/testChildPathOne","hahahahaha".getBytes(),-1);
//			byte[] datas = zk.getData("/testRootPath/testChildPathOne", true, null);
//			String str = new String(datas,"utf-8");
//			System.out.println(str);

			ZkClient client = new ZkClient();
			client.init();
			client.creatNode("aikq");
			client.getChildren("/");

		}catch (Exception e){
			e.printStackTrace();
		}


	}

}
