package org.hegemol.loadbalance.service.impl;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.model.InstanceWarmUpWeight;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class RoundRobinCalculateWeightLoadBalance extends AbstractLoadBalance<InstanceWarmUpWeight> {

    private final int RECYCLE_PERIOD = 60000;

    private final ConcurrentMap<String, ConcurrentMap<String, WeightedRoundRobin>> methodWeightMap = new ConcurrentHashMap<>(16);

    @Override
    protected Instance doLoad(List<InstanceWarmUpWeight> instances, String ip) {
        String key = instances.get(0).getAddress();
        ConcurrentMap<String, WeightedRoundRobin> map = methodWeightMap.computeIfAbsent(key, k -> new ConcurrentHashMap<>(16));
        int totalWeight = 0;
        long maxCurrent = Long.MIN_VALUE;
        long now = System.currentTimeMillis();
        Instance selectedInstance = null;
        WeightedRoundRobin selectedWeightedRoundRobin = null;
        for (InstanceWarmUpWeight instance : instances) {
            String currentKey = instance.getAddress();
            int currentWeight = getWeight(instance);
            WeightedRoundRobin roundRobin = map.computeIfAbsent(currentKey, k -> {
                WeightedRoundRobin weightedRoundRobin = new WeightedRoundRobin();
                weightedRoundRobin.setWeight(currentWeight);
                return weightedRoundRobin;
            });
            if (currentWeight != roundRobin.getWeight()){
                roundRobin.setWeight(currentWeight);
            }
            long current = roundRobin.increaseCurrent();
            roundRobin.setLastUpdate(now);
            if (current > maxCurrent){
                maxCurrent = current;
                selectedInstance = instance;
                selectedWeightedRoundRobin = roundRobin;
            }
            totalWeight += currentWeight;
        }

        if (instances.size() != map.size()){
            map.entrySet().removeIf(item -> now - item.getValue().getLastUpdate()> RECYCLE_PERIOD);
        }
        if (Objects.nonNull(selectedInstance)){
            selectedWeightedRoundRobin.setWeight(totalWeight);
            return selectedInstance;
        }
        return instances.get(0);
    }

    protected static class WeightedRoundRobin {

        private int weight;

        private final AtomicLong current = new AtomicLong(0);

        private long lastUpdate;

        /**
         * Gets weight.
         *
         * @return the weight
         */
        int getWeight() {
            return weight;
        }

        /**
         * Sets weight.
         *
         * @param weight the weight
         */
        void setWeight(final int weight) {
            this.weight = weight;
            current.set(0);
        }

        /**
         * Increase current long.
         *
         * @return the long
         */
        long increaseCurrent() {
            return current.addAndGet(weight);
        }

        /**
         * Sel.
         *
         * @param total the total
         */
        void sel(final int total) {
            current.addAndGet(-1 * total);
        }

        /**
         * Gets last update.
         *
         * @return the last update
         */
        long getLastUpdate() {
            return lastUpdate;
        }

        /**
         * Sets last update.
         *
         * @param lastUpdate the last update
         */
        void setLastUpdate(final long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }
}
