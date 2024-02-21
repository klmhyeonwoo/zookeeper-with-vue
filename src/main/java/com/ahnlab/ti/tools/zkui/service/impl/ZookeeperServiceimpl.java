package com.ahnlab.ti.tools.zkui.service.impl;

import com.ahnlab.ti.tools.zkui.service.ZookeeperService;
import com.ahnlab.ti.tools.zkui.util.zookeeper.ZookeeperTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ZookeeperServiceimpl implements ZookeeperService {

    @Autowired
    private ZookeeperTemplate zookeeperTemplate;
    @Override
    public Map<String, Object> getChildren(String path) {
        return zookeeperTemplate.getChildren(path);
    }
    @Override
    public Map<String, String> getMetaData(String path) {
        return zookeeperTemplate.getMetadata(path);
    }
    @Override
    public String getData(String path) {
        return zookeeperTemplate.getData(path);
    }
    @Override
    public Map<String, String> setData(String path, String value, boolean overwrite) { return zookeeperTemplate.setData(path, value, overwrite); }

}
