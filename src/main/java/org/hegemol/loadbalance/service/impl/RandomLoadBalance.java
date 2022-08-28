package org.hegemol.loadbalance.service.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.hegemol.loadbalance.model.Model;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

/**
 * 随机
 *
 * @author KevinClair
 **/
public class RandomLoadBalance extends AbstractLoadBalance {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    protected Model doLoad(final List<? extends Model> instances) {
        return instances.get(RANDOM.nextInt(instances.size()));
    }
}
