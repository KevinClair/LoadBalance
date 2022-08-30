package org.hegemol.loadbalance.service;

import java.util.List;

import org.hegemol.loadbalance.model.Instance;

/**
 * TODO
 *
 * @author KevinClair
 **/
public interface LoadBalance {


    /**
     * 负载均衡加载器
     *
     * @param instances 可供选择的列表
     * @return 负载均衡后的实例
     */
    Instance load(List<? extends Instance> instances);
}
