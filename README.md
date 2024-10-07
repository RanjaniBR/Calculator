# Calculator Service

## Overview

The **Calculator Service** is a simple Spring Boot application that provides endpoints for basic arithmetic operations and the ability to chain multiple operations. It includes a RESTful API that allows users to perform calculations such as addition, subtraction, multiplication, and division.

## Features

- Health check endpoint to verify service status.
- Calculate endpoint to perform basic arithmetic operations.
- Chain endpoint to perform a series of operations on an initial value.

## Endpoints

### Health Check

- **GET** `/health`
    - **Response**: `RUNNING`

### Calculate

- **GET** `/calculate`
    - **Parameters**:
        - `num1` (double): The first number.
        - `num2` (double): The second number.
        - `operation` (String): The operation to perform (ADD, SUBTRACT, MULTIPLY, DIVIDE).
    - **Response**: Result of the operation as a double.
    - **Error Handling**: Returns a `400 Bad Request` if the operation is invalid.

### Chain Operations

- **POST** `/chain`
    - **Request Body**: JSON object with the following structure:
      ```json
      {
        "initialValue": 10,
        "operations": ["ADD", "SUBTRACT"],
        "numbers": [5, 3]
      }
      ```
    - **Response**: Final result of the chained operations as a double.
    - **Error Handling**: Returns a `400 Bad Request` if any operation is invalid.

## Installation
1. Navigate to the project directory:
   cd calculator-service
2. Build the project using Maven:
   mvn clean install

## Technologies Used
1. Spring Boot
2. Maven
3. JUnit
4. Mockito