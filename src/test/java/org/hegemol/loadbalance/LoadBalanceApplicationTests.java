package org.hegemol.loadbalance;

import org.hegemol.loadbalance.model.ConsistentHashInstance;
import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.model.InstanceWarmUpWeight;
import org.hegemol.loadbalance.model.InstanceWeight;
import org.hegemol.loadbalance.service.LoadBalance;
import org.hegemol.loadbalance.service.impl.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LoadBalanceApplicationTests {


    @Test
    public void random() {
        List<Instance> instances = new ArrayList<>();
        instances.add(new Instance("127.0.0.1"));
        instances.add(new Instance("127.0.0.2"));
        instances.add(new Instance("127.0.0.3"));
        LoadBalance loadBalance = new RandomLoadBalance();
        for (int i = 0; i < 100; i++) {
            System.out.println(loadBalance.load(instances, ""));
        }
    }

    @Test
    public void randomWeight() {
        List<InstanceWeight> instances = new ArrayList<>();
        instances.add(new InstanceWeight("127.0.0.1", 10));
        instances.add(new InstanceWeight("127.0.0.2", 20));
        instances.add(new InstanceWeight("127.0.0.3", 30));
        LoadBalance loadBalance = new RandomWeightLoadBalance();
        for (int i = 0; i < 300; i++) {
            System.out.println(loadBalance.load(instances, ""));
        }
    }

    @Test
    public void randomWarmUpWeight() throws InterruptedException {
        List<InstanceWarmUpWeight> instances = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();
        instances.add(new InstanceWarmUpWeight("127.0.0.1", 10, currentTimeMillis - 2000, 10000));
        instances.add(new InstanceWarmUpWeight("127.0.0.2", 10, currentTimeMillis - 3000, 10000));
        instances.add(new InstanceWarmUpWeight("127.0.0.3", 10, currentTimeMillis - 5000, 10000));
        LoadBalance loadBalance = new RandomWarmUpWeightLoadBalance();
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(10);
            System.out.println(loadBalance.load(instances, ""));
        }
    }

    @Test
    public void hash() {
        List<Instance> instances = new ArrayList<>();
        instances.add(new Instance("127.0.0.1"));
        instances.add(new Instance("127.0.0.2"));
        instances.add(new Instance("127.0.0.3"));
        LoadBalance loadBalance = new HashLoadBalance();
        for (int i = 0; i < 1000; i++) {
            System.out.println(loadBalance.load(instances, "127."+i+".0."+i));
        }
    }

    @Test
    public void consistentHash() {
        List<ConsistentHashInstance> instances = new ArrayList<>();
        instances.add(new ConsistentHashInstance("127.0.0.1", 13));
        instances.add(new ConsistentHashInstance("127.0.0.2", 12));
        instances.add(new ConsistentHashInstance("127.0.0.3", 10));
        LoadBalance loadBalance = new ConsistentHashLoadBalance();
        for (int i = 0; i < 1000; i++) {
            System.out.println(loadBalance.load(instances, "127."+i+".0."+i));
        }
    }

    @Test
    public void roundRobin() {
        List<Instance> instances = new ArrayList<>();
        instances.add(new Instance("127.0.0.1"));
        instances.add(new Instance("127.0.0.2"));
        instances.add(new Instance("127.0.0.3"));
        LoadBalance loadBalance = new RoundRobinLoadBalance();
        for (int i = 0; i < 1000; i++) {
            System.out.println(loadBalance.load(instances, ""));
        }
    }

}
