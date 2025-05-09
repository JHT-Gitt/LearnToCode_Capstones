package org.example;

public class Entries {
    private String description;
    private String vendor;
    private float amount;
    private String date,time;

    public Entries(String description, String vendor, float amount) {
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public Entries(String date, String time, String description, String vendor, float amount) {
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
