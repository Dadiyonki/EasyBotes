package com.example.ultimaoportunidad.models;

public class Expense {
    private String name;
    private float amount;

    public Expense(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }
}
