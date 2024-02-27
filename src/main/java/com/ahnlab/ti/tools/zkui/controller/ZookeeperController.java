package com.ahnlab.ti.tools.zkui.controller;

import com.ahnlab.ti.tools.zkui.config.ZookeeperConfig;
import com.ahnlab.ti.tools.zkui.dto.ClusterDTO;
import com.ahnlab.ti.tools.zkui.service.ZookeeperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ZookeeperController {

    @Autowired
    private ZookeeperService zookeeperService;

    @GetMapping("/api/zkui/clusters/{clusterName}/tree")
    public Map<String, Object> handlerGetChildren(
            @PathVariable String clusterName,
            @RequestParam(required = true) String path) {
        return zookeeperService.getChildren(path, ZookeeperConfig.getCluster(clusterName).split(",")[0]);
    }

    @GetMapping("/api/zkui/clusters/{clusterName}/node")
    public Map<String, Object> handlerGetNode(
            @PathVariable String clusterName,
            @RequestParam(required = true) String path,
            @RequestParam(required = false) boolean meta) {
        Map<String, Object> result = new HashMap<String, Object>(){{
            put("value", zookeeperService.getData(path, ZookeeperConfig.getCluster(clusterName).split(",")[0]));
        }};
        if (meta) //metadata 요청 시 반환
            result.put("metadata", zookeeperService.getMetaData(path, ZookeeperConfig.getCluster(clusterName).split(",")[0]));
        return result;
    }

    @PostMapping(value = "/api/zkui/clusters/{clusterName}/node", consumes = MediaType.TEXT_PLAIN_VALUE)
    public Map<String, String> handlerSetNode(
            @PathVariable String clusterName,
            @RequestParam(required = true) String path,
            @RequestParam(required = false) boolean overwrite,
            @RequestBody(required = true) String value
    ){
        zookeeperService.setData(path, value, ZookeeperConfig.getCluster(clusterName).split(",")[0], overwrite);
        return new HashMap<String, String>(){{
            put("path", path);
            put("value", value);
        }};
    }

    @PostMapping(value = "/api/zkui/clusters", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> handlerAddCluster(
            @Valid @RequestBody ClusterDTO clusterDTO
            ) {
        ZookeeperConfig.addCluster(clusterDTO.getName(), clusterDTO.getHost());
        return new HashMap<String, String>(){{
            put("name", clusterDTO.getName());
            put("host", clusterDTO.getHost());
        }};
    }

    @GetMapping(value = "/api/zkui/clusters")
    public Map<String, String> handlerGetClusters() {
        return ZookeeperConfig.getClusters();
    }

    @GetMapping(value = "/api/zkui/clusters/{clusterName}")
    public Map<String, String> handlerGetCluster(
            @PathVariable String clusterName
    ) {
        return new HashMap<String, String>(){{
            put(clusterName, ZookeeperConfig.getCluster(clusterName));
        }};
    }

}
