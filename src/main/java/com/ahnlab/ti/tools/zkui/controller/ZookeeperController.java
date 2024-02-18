package com.ahnlab.ti.tools.zkui.controller;

import com.ahnlab.ti.tools.zkui.service.ZookeeperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return zookeeperService.getChildren(path);
    }
    @GetMapping("/api/zkui/clusters/{clusterName}/node")
    public Map<String, Object> handlerGetNode(
            @PathVariable String clusterName,
            @RequestParam(required = true) String path,
            @RequestParam(required = false) boolean meta) {
        Map<String, Object> result = new HashMap<>(){{
            put("value",zookeeperService.getData(path));
        }};
        if (meta) //metadata 요청 시 반환
            result.put("metadata", zookeeperService.getMetaData(path));
        return result;
    }
    @PostMapping("/api/zkui/clusters/{clusterName}/node")
    public Map<String, String> handlerSetNode(
            @RequestParam(required = true) String path,
            @RequestParam(required = false) boolean overwrite,
            @RequestBody(required = true) String value
    ){
        zookeeperService.setData(path, value, overwrite);
        return new HashMap<>(){{
            put("path", path);
            put("value", value);
        }};
    }

}
