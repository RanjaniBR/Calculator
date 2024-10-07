package com.ebay.calculator.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.*;
import com.ebay.calculator.exception.InvalidInputException;
import com.ebay.calculator.model.ChainRequest;
import com.ebay.calculator.service.BasicOperation;
import com.ebay.calculator.service.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CalculatorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private CalculatorController calculatorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(calculatorController).build();
    }

    @Test
    public void testHealthCheck() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("RUNNING"));
    }

    @Test
    public void testCalculate_Addition() throws Exception {
        when(calculatorService.performCalculation(5, 3, BasicOperation.ADD)).thenReturn(8.0);

        mockMvc.perform(get("/calculate")
                        .param("num1", "5")
                        .param("num2", "3")
                        .param("operation", "ADD"))
                .andExpect(status().isOk())
                .andExpect(content().string("8.0"));

        verify(calculatorService, times(1)).performCalculation(5, 3, BasicOperation.ADD);
    }

    @Test
    public void testCalculate_InvalidOperation() throws Exception {
        MvcResult result = mockMvc.perform(get("/calculate")
                        .param("num1", "5")
                        .param("num2", "3")
                        .param("operation", "INVALID"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString(); // Response body

        Assertions.assertTrue(content.contains("Invalid Operation. Supported operations are - ADD, SUBTRACT, MULTIPLY, DIVIDE"));

    }

    @Test
    public void testChainOperations() throws Exception {
        ChainRequest request = new ChainRequest(10, new String[]{"ADD", "SUBTRACT"}, new double[]{5, 3});
        when(calculatorService.chainOperations(10, request.getOperations(), request.getNumbers())).thenReturn(12.0);

        mockMvc.perform(post("/chain")
                        .contentType(APPLICATION_JSON)
                        .content("{\"initialValue\":10, \"operations\":[\"ADD\", \"SUBTRACT\"], \"numbers\":[5, 3]}"))
                .andExpect(status().isOk())
                .andExpect(content().string("12.0"));

        verify(calculatorService, times(1)).chainOperations(10, request.getOperations(), request.getNumbers());
    }

    @Test
    public void testChainOperations_InvalidOperation() throws Exception {
        // Mocking the ChainRequest and the service behavior
        ChainRequest request = new ChainRequest(10, new String[]{"ADD", "INVALID"}, new double[]{5, 3});
        when(calculatorService.chainOperations(10, request.getOperations(), request.getNumbers()))
                .thenThrow(new InvalidInputException("Invalid Operation. Supported operations are - ADD, SUBTRACT, MULTIPLY, DIVIDE"));

        MvcResult result = mockMvc.perform(post("/chain")
                        .contentType(APPLICATION_JSON)
                        .content("{\"initialValue\":10, \"operations\":[\"ADD\", \"INVALID\"], \"numbers\":[5, 3]}"))
                .andExpect(status().isBadRequest()) // Expecting 400 status
                .andReturn(); // Capture the result


        String content = result.getResponse().getContentAsString(); // Response body

        Assertions.assertTrue(content.contains("Invalid Operation. Supported operations are - ADD, SUBTRACT, MULTIPLY, DIVIDE"));
    }
}
