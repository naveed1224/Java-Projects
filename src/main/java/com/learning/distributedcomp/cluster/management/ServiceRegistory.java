package com.learning.distributedcomp.cluster.management;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceRegistory implements Watcher {
    private static final String REGISTORY_ZNODE = "/service_registory";
    private final ZooKeeper zooKeeper;
    private String currentZnode = null;
    private List<String> allServiceAddresses;

    public ServiceRegistory(ZooKeeper zooKeeper){
        this.zooKeeper = zooKeeper;
        createServiceRegistoryZnode();
    }

    public void registerToCluster(String metadata) throws InterruptedException, KeeperException {
        this.currentZnode = zooKeeper.create(REGISTORY_ZNODE+ "/n_", metadata.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Registered to service registory");
    }

    private void createServiceRegistoryZnode() {
        try {
            if(zooKeeper.exists(REGISTORY_ZNODE, false) == null){
                zooKeeper.create(REGISTORY_ZNODE, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void updateAddresses() throws InterruptedException, KeeperException {
        List<String> workerZnodes = zooKeeper.getChildren(REGISTORY_ZNODE, this);
        List<String> addresses = new ArrayList<>(workerZnodes.size());

        for(String workerZnode : workerZnodes){
            String workerZnodeFullPath = REGISTORY_ZNODE + "/"+workerZnode;
            Stat stat = zooKeeper.exists(workerZnodeFullPath, false);
            if(stat == null){
                continue;
            }

            byte[] addressBytes = zooKeeper.getData(workerZnodeFullPath, false, stat    );
            String address = new String(addressBytes);
            addresses.add(address);

            this.allServiceAddresses = Collections.unmodifiableList(addresses);
            System.out.println("The cluster addresses are: " + this.allServiceAddresses);


        }
    }

    public void registerForUpdates(){
        try {
            updateAddresses();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            updateAddresses();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }
}
