package com.ahnlab.ti.tools.zkui.util.zookeeper;

import com.ahnlab.ti.tools.zkui.exception.user.DuplicatePathException;
import com.ahnlab.ti.tools.zkui.exception.user.InvalidPathException;
import com.ahnlab.ti.tools.zkui.exception.user.ZnodeNotFoundException;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ZookeeperAccessorTest {

    private ZookeeperAccessor zookeeperAccessor;

    private ZooKeeper mockZookeeper;

    @BeforeEach
    public void beforeEach(){
        mockZookeeper = mock(ZooKeeper.class);
        zookeeperAccessor = new ZookeeperAccessor();
    }

    @ParameterizedTest
    @MethodSource("provideStringsForTest")
    public void checkPath_ThrowsInvalidPathException(String input){
        assertThatThrownBy(() -> zookeeperAccessor.checkPath(input))
                .isInstanceOf(InvalidPathException.class);
    }

    @Test
    public void checkZNode_ThrowsDuplicatePathException() throws InterruptedException, KeeperException {
        //Given, When
        boolean overwrite = false;
        when(mockZookeeper.exists(anyString(), anyBoolean())).thenReturn(new Stat());
        //Then
        assertThatThrownBy(() -> zookeeperAccessor.checkZNode("/", overwrite, mockZookeeper))
                .isInstanceOf(DuplicatePathException.class);
    }

    @Test
    public void checkZNodeStat_ZnodeNotFoundException() throws InterruptedException, KeeperException {
        //Given, When
        when(mockZookeeper.exists(anyString(), anyBoolean())).thenReturn(null);
        //Then
        assertThatThrownBy(() -> zookeeperAccessor.checkZNodeStat("/", mockZookeeper))
                .isInstanceOf(ZnodeNotFoundException.class);
    }

    private static Stream<String> provideStringsForTest() {
        return Stream.of("//","",null,"/test/","test/");
    }

}