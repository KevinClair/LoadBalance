package org.hegemol.loadbalance.model;

/**
 * 虚拟节点个数
 */
public class ConsistentHashInstance extends Instance{

    /**
     * 虚拟节点个数
     */
    private int fictitiousInstance;

    public ConsistentHashInstance() {
    }

    public ConsistentHashInstance(String address, int fictitiousInstance) {
        super(address);
        this.fictitiousInstance = fictitiousInstance;
    }

    public int getFictitiousInstance() {
        return fictitiousInstance;
    }

    public void setFictitiousInstance(int fictitiousInstance) {
        this.fictitiousInstance = fictitiousInstance;
    }
}
