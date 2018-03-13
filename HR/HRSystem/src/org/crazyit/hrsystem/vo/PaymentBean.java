package org.crazyit.hrsystem.vo;

import java.io.Serializable;

public class PaymentBean implements Serializable {
    private static final long serialVersionUID = 48L;

    private String pauMonth;
    private double amount;

    public PaymentBean() {
    }

    public PaymentBean(String pauMonth, double amount) {
        this.pauMonth = pauMonth;
        this.amount = amount;
    }

    public String getPauMonth() {
        return pauMonth;
    }

    public void setPauMonth(String pauMonth) {
        this.pauMonth = pauMonth;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
