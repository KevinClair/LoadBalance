package org.hegemol.loadbalance.model;

/**
 * TODO
 *
 * @author KevinClair
 **/
public class InstanceWeight extends Instance {

    private int weight = 0;

    public InstanceWeight() {
    }

    public InstanceWeight(final String address, final int weight) {
        super(address);
        this.weight = weight;
    }

    /**
     * Gets the value of weight.
     *
     * @return the value of weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight.
     *
     * @param weight weight
     */
    public void setWeight(final int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "InstanceWeight{" +
            "weight=" + weight +
            '}';
    }
}
