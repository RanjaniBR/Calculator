package com.ebay.calculator.model;

public class ChainRequest {
    private double initialValue;
    private String[] operations;
    private double[] numbers;

    public ChainRequest() {
    }

    public ChainRequest(double initialValue, String[] operations, double[] numbers) {
        this.initialValue = initialValue;
        this.operations = operations;
        this.numbers = numbers;
    }

    // Getters and setters
    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }

    public String[] getOperations() {
        return operations;
    }

    public void setOperations(String[] operations) {
        this.operations = operations;
    }

    public double[] getNumbers() {
        return numbers;
    }

    public void setNumbers(double[] numbers) {
        this.numbers = numbers;
    }
}
