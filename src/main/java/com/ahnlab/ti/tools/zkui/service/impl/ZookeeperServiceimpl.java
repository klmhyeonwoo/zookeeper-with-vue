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
    public Map<String, Object> getChildren(String path, String host) { return zookeeperTemplate.getChildren(path, host); }
    @Override
    public Map<String, String> getMetaData(String path, String host) { return zookeeperTemplate.getMetadata(path, host); }
    @Override
    public String getData(String path, String host) {
        return zookeeperTemplate.getData(path, host);
    }
    @Override
    public void setData(String path, String value, String host, boolean overwrite) { zookeeperTemplate.setData(path, value, host, overwrite); }

}
