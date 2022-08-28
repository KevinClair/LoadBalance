package org.hegemol.loadbalance.service.impl;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hegemol.loadbalance.model.Model;
import org.hegemol.loadbalance.model.ModelWeight;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

/**
 * TODO
 *
 * @author KevinClair
 **/
public class RandomWeightLoadBalance extends AbstractLoadBalance {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    protected Model doLoad(final List<? extends Model> models) {
        // 判断权重是否相同


        return null;
    }

    private Map<String, Object> instanceExplain(List<? extends Model> models){
        boolean sameWeight = true;
        for (int i = 0; i < models.size(); i++) {
            ModelWeight modelWeight = (ModelWeight) models.get(i);
            int weight = modelWeight.getWeight();

            // 如果当前的权重不等于上一个实例的权重，那么认为不是相同的权重
            if (i > 0 && weight != ((ModelWeight) models.get(i-1)).getWeight()){
                sameWeight = false;
            }
        }
        return new HashMap<>();
    }
}
