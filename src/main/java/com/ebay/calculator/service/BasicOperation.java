package com.ebay.calculator.service;

import com.ebay.calculator.exception.DivisionByZeroException;
import com.ebay.calculator.operations.Operation;

public enum BasicOperation implements Operation {
    ADD {
        @Override
        public double calculate(double num1, double num2) {
            return num1 + num2;
        }
    },
    SUBTRACT {
        @Override
        public double calculate(double num1, double num2) {
            return num1 - num2;
        }
    },
    MULTIPLY {
        @Override
        public double calculate(double num1, double num2) {
            return num1 * num2;
        }
    },
    DIVIDE {
        @Override
        public double calculate(double num1, double num2) {
            if (num2 == 0) {
                throw new DivisionByZeroException("Division by zero is not allowed");
            }
            return num1 / num2;
        }
    }
}