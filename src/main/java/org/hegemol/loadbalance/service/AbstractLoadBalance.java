package org.hegemol.loadbalance.service;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.model.InstanceWarmUpWeight;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
     * @param ip        客户端Ip地址
     * @return 负载均衡后的实例
     */
    protected abstract Instance doLoad(List<E> instances, String ip);

    @Override
    public Instance load(final List<E> instances, final String ip) {
        if (CollectionUtils.isEmpty(instances)) {
            return null;
        }
        if (instances.size() == 1) {
            return instances.get(0);
        }
        return doLoad(instances, ip);
    }

    /**
     * 通过计算预热时间，动态计算权重值
     *
     * @param instance 当前实例
     * @return
     */
    protected int getWeight(InstanceWarmUpWeight instance) {
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
