package com.ahnlab.ti.tools.zkui.util.zookeeper;

import com.ahnlab.ti.tools.zkui.util.common.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ZookeeperOperation 의 구현 클래스
 */
@Slf4j
@Component
public class ZookeeperTemplate extends ZookeeperAccessor implements ZookeeperOperation{
    public static final String SLASH_STRING = "/";
    public static final String EMPTY_STRING = "";

    @Override
    public Map<String, Object> getChildren(String path, String host) {
        return doWithZookeeper(path, host, con -> {
            checkZNodeStat(path, con); //Znode 없을 시, 에러 발생
            return recursion(con, path);
        });
    }

    @Override
    public String getData(String path, String host) {
        return doWithZookeeper(path, host, con -> {
            checkZNodeStat(path, con); //Znode 없을 시, 에러 발생
            return UserUtils.decodedData(con.getData(path, false, null));
        });
    }

    @Override
    public void setData(String path, String value, String host, boolean overwrite) {
        doWithZookeeper(path, host, con -> {
            checkZNode(path, overwrite, con); //(1) overwrite = false 이며 path 가 존재함 -> 에러 발생(409)

            //(2) overwrite = true 이지만, path 가 존재함 -> set 호출
            if (overwrite && con.exists(path, false) != null) {
                con.setData(path, value.getBytes(), -1);
            } else {
                //그 외의 경우 -> 부모 추가 후, 자식도 추가
                //(3) overwrite = true 이지만, path 가 존재하지 않음
                //(4) overwrite = false 이지만, path 가 존재하지 않음
                addParentZNode(con, path);
                con.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            return null;
        });
    }

    @Override
    public Map<String, String> getMetadata(String path, String host) {
        return doWithZookeeper(path, host, con -> {
            Map<String, String> result = new HashMap<>();
            Stat stat = checkZNodeStat(path, con); //znode 없을 시, 에러 발생
            setStatDetails(result, stat);
            return result;
        });
    }

    /**
     * Znode metadata 저장
     */
    private void setStatDetails(Map<String, String> result, Stat stat) {
        result.put("czxid", String.valueOf(stat.getCzxid()));
        result.put("mzxid", String.valueOf(stat.getMzxid()));
        result.put("ctime", UserUtils.convertMsToDate(stat.getCtime()));
        result.put("mtime", UserUtils.convertMsToDate(stat.getMtime()));
        result.put("version", String.valueOf(stat.getVersion()));
        result.put("cversion", String.valueOf(stat.getCversion()));
        result.put("aversion", String.valueOf(stat.getAversion()));
        result.put("ephemeralOwner", String.valueOf(stat.getEphemeralOwner()));
        result.put("dataLength", String.valueOf(stat.getDataLength()));
        result.put("numChildren", String.valueOf(stat.getNumChildren()));
        result.put("pzxid", String.valueOf(stat.getPzxid()));
    }

    /**
     * 재귀 함수를 사용해서 하위 노드들을 조회한다.
     */
    private Map<String, Object> recursion(ZooKeeper zooKeeper, String node) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        List<String> children = zooKeeper.getChildren(node, false);
        for (String child : children) {
            String childNode = node.equals(SLASH_STRING) ? SLASH_STRING + child : node + SLASH_STRING + child;
            if (zooKeeper.getChildren(childNode, false).isEmpty())
                map.put(child, UserUtils.decodedData(zooKeeper.getData(childNode, false, null)));
            else
                map.put(child, recursion(zooKeeper, childNode));
        }
        return map;
    }

    /**
     * 자식 노드들을 제외한 부모 노드들을 탐색하여, 없다면 생성한다.
     */
    private void addParentZNode(ZooKeeper con, String path) throws InterruptedException, KeeperException {
        String[] pathParts = path.split(SLASH_STRING);
        String pathCheck = EMPTY_STRING;

        for(int i = 1; i < pathParts.length - 1; i++){ //맨 처음 요소는 공백이므로, i = 1부터 시작
            pathCheck += SLASH_STRING + pathParts[i];
            if(con.exists(pathCheck, false) == null){
                con.create(pathCheck, EMPTY_STRING.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT); //부모 노드 추가 시, value 값은 ""을 넣어준다.
            }
        }
    }

}
