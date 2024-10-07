package com.ebay.calculator.service;

import com.ebay.calculator.exception.InvalidInputException;
import com.ebay.calculator.operations.Operation;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CalculatorService {

    public double performCalculation(double num1, double num2, Operation operation) {
        return operation.calculate(num1, num2);
    }

    // Method for chaining operations
    public double chainOperations(double initialValue, String[] operations, double[] numbers) {
        if (operations.length != numbers.length) {
            throw new IllegalArgumentException("Operations and numbers must have the same length");
        }

        double result = initialValue;
        for (int i = 0; i < operations.length; i++) {
            if(!Stream.of(BasicOperation.values())
                    .map(BasicOperation::name)
                    .toList().contains(operations[i].toUpperCase()))
                throw new InvalidInputException("Invalid Operation. Supported operations are - ADD, SUBTRACT, MULTIPLY, DIVIDE");
            Operation op = BasicOperation.valueOf(operations[i].toUpperCase());
            result = performCalculation(result, numbers[i], op);
        }
        return result;
    }

}
