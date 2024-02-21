package com.ahnlab.ti.tools.zkui.util.zookeeper;

import com.ahnlab.ti.tools.zkui.config.ZookeeperConfig;
import com.ahnlab.ti.tools.zkui.exception.user.DuplicatePathException;
import com.ahnlab.ti.tools.zkui.exception.user.GlobalException;
import com.ahnlab.ti.tools.zkui.exception.user.ZnodeNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.common.PathUtils;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Zookeeper Connection 자원 관리와 에러를 처리하는 클래스
 */
@Getter
@Slf4j
public class ZookeeperAccessor {

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    public ZooKeeper getConnection() throws IOException {
        return new ZooKeeper(zookeeperConfig.getHost(), 5000, null);
    }

    public void closeConnection(ZooKeeper zooKeeper) {
        try{
            zooKeeper.close();
        }catch (InterruptedException e){
            log.error(e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public void checkPath(String path){
        PathUtils.validatePath(path);
    }

    public Stat checkZNodeStat(String path, ZooKeeper con) throws InterruptedException, KeeperException {
        Stat stat = null;

        stat = con.exists(path, false);
        if(stat == null)
            throw new ZnodeNotFoundException("Znode not found");

        return stat;
    }

    public void checkZNode(String path, boolean overwrite, ZooKeeper con) throws InterruptedException, KeeperException {
        if(!overwrite && con.exists(path,false) != null)
            throw new DuplicatePathException("Znode already exists");
    }

    public <T> T doWithZookeeper(String path, ZookeeperAction<T> action){
        try{
            checkPath(path);
            ZooKeeper con = getConnection();
            return action.getConnection(con);
        } catch (InterruptedException | KeeperException | IOException e){
            log.error(e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

}
