package com.ahnlab.ti.tools.zkui.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ZookeeperConfig {

    @Value("${host}")
    String host;

    @Value("${name}")
    String name;
}
