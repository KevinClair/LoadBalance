package org.hegemol.loadbalance.model;

/**
 * TODO
 *
 * @author KevinClair
 **/
public class Instance {

    private String address;

    public Instance() {
    }

    public Instance(final String address) {
        this.address = address;
    }

    /**
     * Gets the value of address.
     *
     * @return the value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address address
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Instance{" +
            "address='" + address + '\'' +
            '}';
    }
}
