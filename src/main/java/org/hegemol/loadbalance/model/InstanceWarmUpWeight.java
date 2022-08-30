package org.hegemol.loadbalance.model;

/**
 * 预热时间的动态权重
 *
 * @author KevinClair
 **/
public class InstanceWarmUpWeight extends InstanceWeight {

    // 启动时间
    private long startUpTime;

    // 预热时间
    private int warmUpTime;

    public InstanceWarmUpWeight() {
    }

    public InstanceWarmUpWeight(final String address, final int weight, final long startUpTime, final int warmUpTime) {
        super(address, weight);
        this.startUpTime = startUpTime;
        this.warmUpTime = warmUpTime;
    }

    /**
     * Gets the value of startUpTime.
     *
     * @return the value of startUpTime
     */
    public long getStartUpTime() {
        return startUpTime;
    }

    /**
     * Sets the startUpTime.
     *
     * @param startUpTime startUpTime
     */
    public void setStartUpTime(final long startUpTime) {
        this.startUpTime = startUpTime;
    }

    /**
     * Gets the value of warmUpTime.
     *
     * @return the value of warmUpTime
     */
    public int getWarmUpTime() {
        return warmUpTime;
    }

    /**
     * Sets the warmUpTime.
     *
     * @param warmUpTime warmUpTime
     */
    public void setWarmUpTime(final int warmUpTime) {
        this.warmUpTime = warmUpTime;
    }

    @Override
    public String toString() {
        return "InstanceWarmUpWeight{" +
            "address=" + getAddress() +
            ", startUpTime=" + startUpTime +
            ", warmUpTime=" + warmUpTime +
            ", weight=" + getWeight() +
            '}';
    }
}
