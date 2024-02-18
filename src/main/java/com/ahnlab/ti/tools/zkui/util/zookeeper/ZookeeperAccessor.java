package com.ahnlab.ti.tools.zkui.util.zookeeper;

import com.ahnlab.ti.tools.zkui.config.ZookeeperConfig;
import com.ahnlab.ti.tools.zkui.exception.user.DuplicatePathException;
import com.ahnlab.ti.tools.zkui.exception.user.GlobalException;
import com.ahnlab.ti.tools.zkui.exception.user.InvalidPathException;
import com.ahnlab.ti.tools.zkui.exception.user.ZnodeNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Zookeeper Connection 자원 관리와 사용자가 정의한 에러만 처리하는 클래스이다.
 * 그 외 에러 처리는 ZookeeperTemplate 클래스에서 처리한다.
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
        validatePath(path);
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

    public void validatePath(String path) {
        if (path == null)
            throw new InvalidPathException("Path cannot be null");

        if (path.length() == 0)
            throw new InvalidPathException("Path length must be > 0");

        if (path.charAt(0) != '/')
            throw new InvalidPathException("Path must start with / character");

        if (path.length() == 1)  // done checking - it's the root
            return;

        if (path.charAt(path.length() - 1) == '/')
            throw new InvalidPathException("Path must not end with / character");

        String reason = null;
        char lastc = '/';
        char[] chars = path.toCharArray();
        char c;
        for (int i = 1; i < chars.length; lastc = chars[i], i++) {
            c = chars[i];

            if (c == 0) {
                reason = "null character not allowed @" + i;
                break;
            } else if (c == '/' && lastc == '/') {
                reason = "empty node name specified @" + i;
                break;
            } else if (c == '.' && lastc == '.') {
                if (chars[i - 2] == '/' && ((i + 1 == chars.length) || chars[i + 1] == '/')) {
                    reason = "relative paths not allowed @" + i;
                    break;
                }
            } else if (c == '.') {
                if (chars[i - 1] == '/' && ((i + 1 == chars.length) || chars[i + 1] == '/')) {
                    reason = "relative paths not allowed @" + i;
                    break;
                }
            } else if (c > '\u0000' && c <= '\u001f'
                    || c >= '\u007f' && c <= '\u009F'
                    || c >= '\ud800' && c <= '\uf8ff'
                    || c >= '\ufff0' && c <= '\uffff') {
                reason = "invalid character @" + i;
                break;
            }
        }

        if (reason != null) {
            throw new InvalidPathException("Invalid path string \"" + path + "\" caused by " + reason);
        }
    }

}
