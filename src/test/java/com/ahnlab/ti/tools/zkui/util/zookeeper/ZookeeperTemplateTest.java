package com.ahnlab.ti.tools.zkui.util.zookeeper;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ZookeeperTemplateTest {

    private ZooKeeper mockZookeeper;
    private ZookeeperTemplate mockZookeeperTemplate;
    private static final String host = "testhost";

    @BeforeEach
    public void beforeEach() throws IOException, InterruptedException, KeeperException {
        mockZookeeper = mock(ZooKeeper.class);
        mockZookeeperTemplate = spy(new ZookeeperTemplate());
        doReturn(mockZookeeper).when(mockZookeeperTemplate).getConnection(host);
        doReturn(new Stat()).when(mockZookeeperTemplate).checkZNodeStat(anyString(), any());
    }

    @Test
    public void getChildrenTest() throws InterruptedException, KeeperException {
        //Given
        when(mockZookeeper.getChildren(anyString(), eq(false))).thenReturn(Arrays.asList("child1","child2"));

        when(mockZookeeper.getChildren("/child1", false)).thenReturn(Arrays.asList("grandchild1"));
        when(mockZookeeper.getChildren("/child2", false)).thenReturn(Arrays.asList("grandchild2"));

        when(mockZookeeper.getChildren("/child1/grandchild1",false)).thenReturn(new ArrayList<>());
        when(mockZookeeper.getChildren("/child2/grandchild2",false)).thenReturn(new ArrayList<>());

        when(mockZookeeper.getData("/child1", false, null)).thenReturn("data1".getBytes());
        when(mockZookeeper.getData("/child2", false, null)).thenReturn("data2".getBytes());
        when(mockZookeeper.getData("/child1/grandchild1", false, null)).thenReturn("data3".getBytes());
        when(mockZookeeper.getData("/child2/grandchild2", false, null)).thenReturn("data4".getBytes());

        Map<String, Object> expectedResult = new HashMap<>();
        Map<String, Object> child1Map = new HashMap<>();
        Map<String, Object> child2Map = new HashMap<>();
        child1Map.put("grandchild1", "data3");
        child2Map.put("grandchild2", "data4");

        expectedResult.put("child1", child1Map);
        expectedResult.put("child2", child2Map);
        //When
        Map<String, Object> result = mockZookeeperTemplate.getChildren("/", host);
        //Then
        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    public void getDataTest() throws InterruptedException, KeeperException {
        //Given
        String path = "/path";
        String expectedResult = "data";
        when(mockZookeeper.getData(path, false, null)).thenReturn(expectedResult.getBytes());
        //When
        String result = mockZookeeperTemplate.getData(path, host);
        //Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("overwrite = true 이지만, path 가 존재함 -> set 호출")
    public void setDataTest1() throws InterruptedException, KeeperException {
        //Given
        String path = "/path";
        String value = "value";
        Stat mockStat = mock(Stat.class);

        doReturn(mockStat).when(mockZookeeper).exists(anyString(), anyBoolean());
        //When, Then
        assertDoesNotThrow(() -> mockZookeeperTemplate.setData(path, value, host, true));
    }

    @Test
    @DisplayName("overwrite = true, false && path 존재하지 않음 -> 부모 생성 후, 자식 생성")
    public void setDataTest2() throws InterruptedException, KeeperException {
        //Given
        String path = "/parent/child";

        doReturn(null).when(mockZookeeper).exists(anyString(), anyBoolean());
        doReturn(null).when(mockZookeeper).create(anyString(), any(), any(), any());

        try{
            Method method = ZookeeperTemplate.class.getDeclaredMethod("addParentZNode", ZooKeeper.class, String.class);
            method.setAccessible(true);
            method.invoke(mockZookeeperTemplate, mockZookeeper, path);

            //When, Then
            //부모만 생성하므로, addParentZNode 함수는 1번만 실행
            verify(mockZookeeper, times(1)).exists(anyString(), anyBoolean());
            verify(mockZookeeper, times(1)).create(anyString(), any(), any(), any());
        } catch (Exception e){
            fail("failed to invoke private method", e);
        }
    }

    @Test
    public void getMetadataTest() throws InterruptedException, KeeperException {
        //Given
        String key = "czxid";
        Stat mockStat = new Stat();
        mockStat.setCzxid(123L);

        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put(key, String.valueOf(mockStat.getCzxid()));

        doReturn(mockStat).when(mockZookeeperTemplate).checkZNodeStat(anyString(), any());
        //When
        Map<String, String> result = mockZookeeperTemplate.getMetadata("/", host);
        //Then
        Assertions.assertEquals(expectedResult.get(key), result.get(key));
    }

}
