package com.tanzimkabir.libmansys.controller;

import com.tanzimkabir.libmansys.model.TransactionRequest;
import com.tanzimkabir.libmansys.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class contains endpoints for issuing Books to Users and for Users submitting Books.
 *
 * @author tanzim
 */

@Slf4j
@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/issue", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> issueBook(@RequestHeader(value = "request-id") String requestId, @RequestBody TransactionRequest transactionRequest) {
        MDC.put("request_id", requestId);
        if (transactionService.issueBook(transactionRequest)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> submitBook(@RequestHeader(value = "request-id") String requestId, @RequestBody TransactionRequest transactionRequest) {
        MDC.put("request_id", requestId);
        if (transactionService.submitBook(transactionRequest)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
