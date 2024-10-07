package com.ebay.calculator.controller;


import com.ebay.calculator.exception.ErrorResponse;
import com.ebay.calculator.exception.InvalidInputException;
import com.ebay.calculator.model.ChainRequest;
import com.ebay.calculator.operations.Operation;
import com.ebay.calculator.service.BasicOperation;
import com.ebay.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Stream;

@RestController
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/health")
    public String healthCheck() {
       return "RUNNING";
    }

    @GetMapping("/calculate")
    public double calculate(@RequestParam double num1, @RequestParam double num2, @RequestParam String operation) throws InvalidInputException {
        if(!Stream.of(BasicOperation.values())
                .map(BasicOperation::name)
                .toList().contains(operation.toUpperCase()))
            throw new InvalidInputException("Invalid Operation. Supported operations are - ADD, SUBTRACT, MULTIPLY, DIVIDE");
        Operation op = BasicOperation.valueOf(operation.toUpperCase());
        return calculatorService.performCalculation(num1, num2, op);
    }

    @PostMapping("/chain")
    public ResponseEntity<Double> chain(@RequestBody ChainRequest request) {
        double result = calculatorService.chainOperations(request.getInitialValue(), request.getOperations(), request.getNumbers());
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}