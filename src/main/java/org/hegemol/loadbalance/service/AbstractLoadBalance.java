package org.hegemol.loadbalance.service;

import java.util.List;

import org.hegemol.loadbalance.model.Instance;
import org.springframework.util.CollectionUtils;

/**
 * TODO
 *
 * @author KevinClair
 **/
public abstract class AbstractLoadBalance<E extends Instance> implements LoadBalance<E> {

    /**
     * 抽象方法
     *
     * @param instances 实例列表
     * @return
     */
    protected abstract Instance doLoad(List<E> instances);

    @Override
    public Instance load(final List<E> instances) {
        if (CollectionUtils.isEmpty(instances)) {
            return null;
        }
        if (instances.size() == 1) {
            return instances.get(0);
        }
        return doLoad(instances);
    }
}
