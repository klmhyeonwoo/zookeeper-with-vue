package com.ahnlab.ti.tools.zkui.service;

import java.util.Map;

public interface ZookeeperService {
    Map<String, Object> getChildren(String path, String host);
    Map<String, String> getMetaData(String path, String host);
    String getData(String path, String host);
    void setData(String path, String value, String host, boolean overwrite);
}
