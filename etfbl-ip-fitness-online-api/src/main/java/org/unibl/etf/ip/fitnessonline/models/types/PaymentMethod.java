package org.unibl.etf.ip.fitnessonline.models.types;

public enum PaymentMethod {
    CASH("CASH"),
    CREDIT_CARD("CREDIT_CARD"),
    PAYPAL("PAYPAL");

    private final String value;

    private PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
