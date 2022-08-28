package org.hegemol.loadbalance.model;

/**
 * TODO
 *
 * @author KevinClair
 **/
public class Model {

    private String address;

    public Model() {
    }

    public Model(final String address) {
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
        return "Model{" +
            "address='" + address + '\'' +
            '}';
    }
}
