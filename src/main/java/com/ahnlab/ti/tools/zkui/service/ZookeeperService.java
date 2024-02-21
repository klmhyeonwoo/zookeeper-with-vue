package com.ahnlab.ti.tools.zkui.service;

import java.util.Map;

public interface ZookeeperService {
    Map<String, Object> getChildren(String path);
    Map<String, String> getMetaData(String path);
    String getData(String path);
    void setData(String path, String value, boolean overwrite);
}
