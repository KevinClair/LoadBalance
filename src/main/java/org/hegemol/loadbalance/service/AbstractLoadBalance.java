package org.hegemol.loadbalance.service;

import java.util.List;

import org.hegemol.loadbalance.model.Model;
import org.springframework.util.CollectionUtils;

/**
 * TODO
 *
 * @author KevinClair
 **/
public abstract class AbstractLoadBalance implements LoadBalance{

    /**
     * 抽象方法
     *
     * @param instances 实例列表
     * @return
     */
    protected abstract Model doLoad(List<? extends Model> instances);

    @Override
    public Model load(final List<? extends Model> instances) {
        if (CollectionUtils.isEmpty(instances)){
            return null;
        }
        if (instances.size() == 1){
            return instances.get(0);
        }
        return doLoad(instances);
    }
}
