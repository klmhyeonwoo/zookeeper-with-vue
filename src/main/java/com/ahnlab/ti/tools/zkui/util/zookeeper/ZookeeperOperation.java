package com.ahnlab.ti.tools.zkui.util.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 ZookeeperTemplate 기능 명세
 */
public interface ZookeeperOperation {
    Map<String, Object> getChildren(String path);
    Map<String, String> getMetadata(String path);
    String getData(String path) throws InterruptedException, KeeperException, UnsupportedEncodingException;
    void setData(String path, String value, boolean overwrite);
}
