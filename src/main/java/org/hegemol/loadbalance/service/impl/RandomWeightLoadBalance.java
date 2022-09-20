package org.hegemol.loadbalance.service.impl;

import java.security.SecureRandom;
import java.util.List;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.model.InstanceWeight;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

/**
 * 随机权重
 *
 * @author KevinClair
 **/
public class RandomWeightLoadBalance extends AbstractLoadBalance<InstanceWeight> {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    protected Instance doLoad(final List<InstanceWeight> instances) {
        int length = instances.size();
        // 判断权重是否相同
        boolean sameWeight = true;
        // 记录所有的权重值
        int[] weights = new int[length];
        int firstInstanceWeight = (instances.get(0)).getWeight();
        // 记录第一个值
        weights[0] = firstInstanceWeight;
        // 记录总的权重
        int totalWeight = firstInstanceWeight;
        // 计算总权重和是否是相同的权重
        for (int i = 1; i < length; i++) {
            int currentInstanceWeight = (instances.get(i)).getWeight();
            weights[i] = currentInstanceWeight;
            totalWeight += currentInstanceWeight;
            if (sameWeight && currentInstanceWeight != firstInstanceWeight) {
                sameWeight = false;
            }
        }

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
}
