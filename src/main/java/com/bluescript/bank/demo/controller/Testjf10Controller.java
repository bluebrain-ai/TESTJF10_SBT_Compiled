package com.bluescript.bank.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import com.bluescript.bank.demo.service.ITestjf10Service;

@Getter
@Setter
@RequiredArgsConstructor
@Component
@Log4j2
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class Testjf10Controller {
    @Autowired
    private ITestjf10Service testjf10Service;

    @Operation(description = "Desc-API", summary = "Summary", responses = {
            @ApiResponse(responseCode = "400", description = "This is a bad request, please follow the API documentation for the proper request format"),
            @ApiResponse(responseCode = "401", description = "Due to security constraints, your access request cannot be authorized"),
            @ApiResponse(responseCode = "500", description = "The server/Application is down. Please contact support team.") })
    @PostMapping("/caller")
    public String testjf10Service() {

        return testjf10Service.mainModule(); /* Add parameters from service layer */
    }
}