package org.hegemol.loadbalance.service.impl;

import java.util.List;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

/**
 * Hash负载均衡
 *
 * @author KevinClair
 **/
public class HashLoadBalance extends AbstractLoadBalance<Instance> {


    @Override
    protected Instance doLoad(final List<Instance> instances, final String ip) {
        return null;
    }
}
