package com.learning.distributedcomp.cluster.management;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;

public class LeaderElection implements Watcher {

    private final ZooKeeper zooKeeper;
    private static final String ELECTION_NAMESPACE = "/election";
    private String currentZnodeName;


    public LeaderElection(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }


    public void leaderElection() throws InterruptedException, KeeperException {
        //called by each node upon connection to zookeeper
        String znodePrefix = ELECTION_NAMESPACE + "/c_";
        String znodeFullPath = zooKeeper.create(znodePrefix, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println("znode name: "+znodeFullPath);
        this.currentZnodeName = znodeFullPath.replace(ELECTION_NAMESPACE+ "/", "");
    }

    public void reElectLeader() throws InterruptedException, KeeperException {
        //determines if current node is the leader or not
        Stat predecessorStat = null;
        String predecessorZnodeName = "";

        while (predecessorStat == null){
            List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
            Collections.sort(children);

            children.forEach((child) -> System.out.println("Child: " + child));

            String smallestChild = children.get(0);

            if(smallestChild.equals(currentZnodeName)){
                System.out.println("I am the leader");
                return;
            } else {
                System.out.println("I am not the leader");
                int predecessorIndex = Collections.binarySearch(children, currentZnodeName) -1;
                predecessorZnodeName = children.get(predecessorIndex);
                predecessorStat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + predecessorZnodeName, this);
            }
        }

        System.out.println("Watching znode" + predecessorZnodeName);
        System.out.println();

    }

//    public void watchTargetZnode() throws InterruptedException, KeeperException {
//        Stat stat = zooKeeper.exists(TARGET_ZNODE, this);
//        if (stat == null){
//            return;
//        }
//
//        byte [] data = zooKeeper.getData(TARGET_ZNODE, this, stat);
//        List<String> children = zooKeeper.getChildren(TARGET_ZNODE, this);
//
//        System.out.println("Data: "+ new String(data) + "Children: " + children );
//    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {

            case NodeDeleted:
                System.out.println("Node was deleted");
                try {
                    reElectLeader();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (KeeperException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}


//    public void process(WatchedEvent watchedEvent) {
//        switch (watchedEvent.getType()){
//
//            case None:
//                if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
//                    System.out.println("Successfully connected to Zookeeper");
//                } else {
//                    //checking for close state(when loose connection to zookeeper) by waking up main thread and allow application to close by notifying all on zookeeper object
//                    synchronized (zooKeeper){
//                        System.out.println("Disconnected from Zookeeper event");
//                        zooKeeper.notifyAll();
//                    }
//                }
//
//            case NodeDeleted:
//                System.out.println("Node was deleted");
//                try {
//                    reElectLeader();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case NodeCreated:
//                System.out.println("Node was created");
//                break;
//            case NodeChildrenChanged:
//                System.out.println("Node Children was changed");
//                break;
//            case NodeDataChanged:
//                System.out.println("Node data was changed");
//                break;
//        }
//
//        try {
//            watchTargetZnode();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (KeeperException e) {
//            e.printStackTrace();
//        }
//    }
