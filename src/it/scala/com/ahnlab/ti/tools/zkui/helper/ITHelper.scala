package com.ahnlab.ti.tools.zkui.helper

import com.ahnlab.ti.tools.zkui.util.zookeeper.ZookeeperAccessor
import org.apache.zookeeper.{CreateMode, ZooDefs}

object ITHelper {
  // '6.3.2 Path 조회' 를 위한 Mock 데이터
  val PARENT_NODE = "/node"
  val CHILD_NODE1 = "/node/child1"
  val CHILD_NODE2 = "/node/child2"
  val data1 = "value1"
  val data2 = "value2"

  def insertMockDatas(): Unit = {
    val zk = new ZookeeperAccessor().getConnection(ZkConfig.DaZkHosts)
    zk.create(PARENT_NODE, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    zk.create(CHILD_NODE1, data1.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    zk.create(CHILD_NODE2, data2.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    zk.close()
  }

  def deleteMockDatas(): Unit = {
    val zk = new ZookeeperAccessor().getConnection(ZkConfig.DaZkHosts)
    zk.delete(CHILD_NODE1, -1)
    zk.delete(CHILD_NODE2, -1)
    zk.delete(PARENT_NODE, -1)
    zk.close()
  }

}
