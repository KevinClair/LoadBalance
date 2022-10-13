package org.hegemol.loadbalance.service.impl;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance<Instance> {

    private static final AtomicInteger index = new AtomicInteger(0);

    @Override
    protected Instance doLoad(List<Instance> instances, String ip) {
        index.compareAndSet(instances.size(), 0);
        return instances.get(index.getAndIncrement());
    }
}
