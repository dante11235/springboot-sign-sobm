package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @Operation(summary = "Get a greeting message")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved greeting")
    @GetMapping("/hello")
    public Map<String, Object> hello(@RequestParam(required = false, defaultValue = "World") String name) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello, " + name + "!");
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
} 