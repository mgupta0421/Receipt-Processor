package com.example.receiptprocessor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.service.ReceiptService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;
    
    public ReceiptController(ReceiptService receiptService){
        this.receiptService = receiptService;
    }

    @ApiOperation("Provide the unique ID of the processed receipt")
    @PostMapping("/process")
    public ResponseEntity<String> processReceipt(@RequestBody Receipt receipt){
        Receipt savedReceipt = receiptService.processReceipt(receipt);
        return ResponseEntity.ok().body("{ \"id\": \"" + savedReceipt.getId() + "\"}");
    }

    @ApiOperation("Retrieve the total points for the provided id")
    @GetMapping("{id}/points")
    public ResponseEntity<String> getPoints(@PathVariable String id){
        int points = receiptService.getPoints(id);
        return ResponseEntity.ok().body("{ \"points\": " + points + " }");
    }

}
