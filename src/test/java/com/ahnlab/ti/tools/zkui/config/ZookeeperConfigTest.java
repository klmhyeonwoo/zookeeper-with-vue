package com.ahnlab.ti.tools.zkui.config;

import com.ahnlab.ti.tools.zkui.exception.user.DuplicateNameException;
import com.ahnlab.ti.tools.zkui.exception.user.HostNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ZookeeperConfigTest {

    @Test
    public void addCluster_DuplicateNameException() {
        //Given, When
        ZookeeperConfig.addCluster("asd-app", "localhost:2181, localhost:2182");
        //Then
        assertThatThrownBy(() -> ZookeeperConfig.addCluster("asd-app", "localhost:2181"))
                .isInstanceOf(DuplicateNameException.class);
        //테스트 데이터 제거
        ZookeeperConfig.getClusters().remove("asd-app");
    }

    @Test
    public void getCluster_HostNotFoundException() {
        //Given, When, Then
        assertThatThrownBy(() -> ZookeeperConfig.getCluster("test-app"))
                .isInstanceOf(HostNotFoundException.class);
    }

    @Test
    public void addClusterTest() {
        //Given
        String name = "asd-app";
        String host = "localhost:2181, localhost:2182";
        //When
        ZookeeperConfig.addCluster(name, host);
        String expectedResult = ZookeeperConfig.getCluster("asd-app");
        //Then
        Assertions.assertEquals(expectedResult, host);
        //테스트 데이터 제거
        ZookeeperConfig.getClusters().remove("asd-app");
    }

    @Test
    public void getClustersTest() {
        //Given
        String name1 = "asd-app";
        String host1 = "localhost:2181, localhost:2182";

        String name2 = "ti-app";
        String host2 = "localhost:2183, localhost:2184";

        ZookeeperConfig.addCluster(name1, host1);
        ZookeeperConfig.addCluster(name2, host2);

        Map<String, String> expectedResult = new HashMap<String, String>() {{
            put(name1, host1);
            put(name2, host2);
        }};
        //When
        Map<String, String> result = ZookeeperConfig.getClusters();
        //Then
        Assertions.assertEquals(expectedResult, result);
        //테스트 데이터 제거
        ZookeeperConfig.getClusters().remove(name1);
        ZookeeperConfig.getClusters().remove(name2);
    }

    @Test
    public void getClusterTest() {
        //Given
        String name = "asd-app";
        String host = "localhost:2181, localhost:2182";

        ZookeeperConfig.addCluster(name, host);
        //When
        String result = ZookeeperConfig.getCluster(name);
        //Then
        Assertions.assertEquals(host, result);
        //테스트 데이터 제거
        ZookeeperConfig.getClusters().remove(name);
    }

}
