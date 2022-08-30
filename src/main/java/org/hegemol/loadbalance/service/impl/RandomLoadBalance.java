package org.hegemol.loadbalance.service.impl;

import java.security.SecureRandom;
import java.util.List;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

/**
 * 随机
 *
 * @author KevinClair
 **/
public class RandomLoadBalance extends AbstractLoadBalance {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    protected Instance doLoad(final List<? extends Instance> instances) {
        return instances.get(RANDOM.nextInt(instances.size()));
    }
}
