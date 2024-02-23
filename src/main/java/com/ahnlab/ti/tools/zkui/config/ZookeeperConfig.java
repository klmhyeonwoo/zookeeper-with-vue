package com.ahnlab.ti.tools.zkui.config;

import com.ahnlab.ti.tools.zkui.exception.user.DuplicateNameException;
import com.ahnlab.ti.tools.zkui.exception.user.HostNotFoundException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Zookeeper Cluster 정보를 처리하는 클래스
 */
public class ZookeeperConfig {

    @Getter
    private static Map<String, String> clusters = new HashMap<>(); // key : server name, value : host

    public static void addCluster(String name, String host) {
        if(clusters.containsKey(name))
            throw new DuplicateNameException("Duplicate host name"); //중복되는 server name 이 있으면 409에러 발생

        clusters.put(name, host);
    }

    public static String getCluster(String name) {
        if(!clusters.containsKey(name))
            throw new HostNotFoundException("Host doesn't found"); //저장되어 있는 host 가 없으면 204 발생

        return clusters.get(name);
    }

}
