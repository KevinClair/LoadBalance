package org.hegemol.loadbalance.service.impl;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.model.InstanceWarmUpWeight;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 加权轮询
 */
public class RoundRobinWeightLoadBalance extends AbstractLoadBalance<InstanceWarmUpWeight> {

    private static final AtomicInteger index = new AtomicInteger(0);

    @Override
    protected Instance doLoad(List<InstanceWarmUpWeight> instances, String ip) {
        int totalWeight = 0;
        int[] weights = new int[instances.size()];
        for (int i = 0; i < instances.size(); i++) {
            int currentWeight = getWeight(instances.get(i));
            weights[i] = currentWeight;
            totalWeight += currentWeight;
        }
        int number = index.getAndIncrement() % totalWeight;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] > number){
                return instances.get(i);
            }
            number -= weights[i];
        }
        return null;
    }
}