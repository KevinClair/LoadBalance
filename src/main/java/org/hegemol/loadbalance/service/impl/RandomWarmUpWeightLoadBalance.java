package org.hegemol.loadbalance.service.impl;

import java.security.SecureRandom;
import java.util.List;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.model.InstanceWarmUpWeight;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

import com.alibaba.fastjson.JSON;

/**
 * 随机权重算法，预热加载
 *
 * @author KevinClair
 **/
public class RandomWarmUpWeightLoadBalance extends AbstractLoadBalance {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    protected Instance doLoad(final List<? extends Instance> instances) {
        int length = instances.size();
        // 判断权重是否相同
        boolean sameWeight = true;
        // 记录所有的权重值
        int[] weights = new int[length];
        int firstInstanceWeight = getWeight((InstanceWarmUpWeight) instances.get(0));
        // 记录第一个值
        weights[0] = firstInstanceWeight;
        // 记录总的权重
        int totalWeight = firstInstanceWeight;
        // 计算总权重和是否是相同的权重
        for (int i = 1; i < length; i++) {
            int currentInstanceWeight = getWeight((InstanceWarmUpWeight) instances.get(i));
            weights[i] = currentInstanceWeight;
            totalWeight += currentInstanceWeight;
            if (sameWeight && currentInstanceWeight != firstInstanceWeight) {
                sameWeight = false;
            }
        }
        System.out.println("当前参与计算的权重值分别为:" + JSON.toJSONString(weights));

        // 如果总权重>0且权重值不相同
        if (totalWeight > 0 && !sameWeight) {
            int offset = RANDOM.nextInt(totalWeight);
            for (int i = 0; i < length; i++) {
                offset -= weights[i];
                if (offset < 0) {
                    return instances.get(i);
                }
            }
        }
        return instances.get(RANDOM.nextInt(instances.size()));
    }

    /**
     * 通过计算预热时间，动态计算权重值
     *
     * @param instance 当前实例
     * @return
     */
    private int getWeight(InstanceWarmUpWeight instance) {
        if (instance.getWeight() > 0 && instance.getStartUpTime() > 0) {
            int betweenTime = (int) (System.currentTimeMillis() - instance.getStartUpTime());
            if (betweenTime > 0 && betweenTime < instance.getWarmUpTime()) {
                int ww = (int) ((float) betweenTime / ((float) instance.getWarmUpTime() / (float) instance.getWeight()));
                return ww < 1 ? 1 : (Math.min(ww, instance.getWeight()));
            }
        }
        return instance.getWeight();
    }
}
