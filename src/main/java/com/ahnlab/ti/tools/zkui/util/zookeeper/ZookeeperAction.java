package com.ahnlab.ti.tools.zkui.util.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.UnsupportedEncodingException;

public interface ZookeeperAction<T> {
    T getConnection(ZooKeeper con) throws InterruptedException, KeeperException, UnsupportedEncodingException;
}
