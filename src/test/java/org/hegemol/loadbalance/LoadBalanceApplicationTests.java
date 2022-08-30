package org.hegemol.loadbalance;

import java.util.ArrayList;
import java.util.List;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.model.InstanceWarmUpWeight;
import org.hegemol.loadbalance.model.InstanceWeight;
import org.hegemol.loadbalance.service.LoadBalance;
import org.hegemol.loadbalance.service.impl.RandomLoadBalance;
import org.hegemol.loadbalance.service.impl.RandomWarmUpWeightLoadBalance;
import org.hegemol.loadbalance.service.impl.RandomWeightLoadBalance;
import org.junit.jupiter.api.Test;

class LoadBalanceApplicationTests {


    @Test
    public void random() {
        List<Instance> instances = new ArrayList<>();
        instances.add(new Instance("127.0.0.1"));
        instances.add(new Instance("127.0.0.2"));
        instances.add(new Instance("127.0.0.3"));
        LoadBalance loadBalance = new RandomLoadBalance();
        for (int i = 0; i < 100; i++) {
            System.out.println(loadBalance.load(instances));
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
            System.out.println(loadBalance.load(instances));
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
            System.out.println(loadBalance.load(instances));
        }
    }

}
