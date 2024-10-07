package com.ebay.calculator.service;

import static org.junit.jupiter.api.Assertions.*;

import com.ebay.calculator.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    public void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    public void testPerformCalculation_Addition() {
        double result = calculatorService.performCalculation(5, 3, BasicOperation.ADD);
        assertEquals(8, result);
    }

    @Test
    public void testPerformCalculation_Subtraction() {

        double result = calculatorService.performCalculation(5, 3, BasicOperation.SUBTRACT);
        assertEquals(2, result);
    }

    @Test
    public void testPerformCalculation_Multiplication() {

        double result = calculatorService.performCalculation(5, 3, BasicOperation.MULTIPLY);
        assertEquals(15, result);
    }

    @Test
    public void testPerformCalculation_Division() {

        double result = calculatorService.performCalculation(6, 3, BasicOperation.DIVIDE);
        assertEquals(2, result);
    }

    @Test
    public void testChainOperations_ValidOperations() {
        String[] operations = {"ADD", "SUBTRACT", "MULTIPLY"};
        double[] numbers = {5, 3, 2};
        double result = calculatorService.chainOperations(10, operations, numbers);
        assertEquals(24, result); // (10 + 5 - 3) * 2 = 24
    }

    @Test
    public void testChainOperations_InvalidOperation() {
        String[] operations = {"ADD", "INVALID", "MULTIPLY"};
        double[] numbers = {5, 3, 2};
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            calculatorService.chainOperations(10, operations, numbers);
        });

        assertEquals("Invalid Operation. Supported operations are - ADD, SUBTRACT, MULTIPLY, DIVIDE", exception.getMessage());
    }

    @Test
    public void testChainOperations_DifferentLengths() {
        String[] operations = {"ADD"};
        double[] numbers = {5, 3};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.chainOperations(10, operations, numbers);
        });

        assertEquals("Operations and numbers must have the same length", exception.getMessage());
    }

    @Test
    public void testChainOperations_EmptyInputs() {
        String[] operations = {};
        double[] numbers = {};
        double result = calculatorService.chainOperations(10, operations, numbers);
        assertEquals(10, result); // Initial value remains unchanged
    }
}