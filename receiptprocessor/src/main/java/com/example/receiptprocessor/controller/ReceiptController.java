package com.example.receiptprocessor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.service.ReceiptService;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;
    
    public ReceiptController(ReceiptService receiptService){
        this.receiptService = receiptService;
    }
    @PostMapping("/process")
    public ResponseEntity<String> processReceipt(@RequestBody Receipt receipt){
        Receipt savedReceipt = receiptService.processReceipt(receipt);
        return ResponseEntity.ok().body("{ \"id\": \"" + savedReceipt.getId() + "\"}");
    }

}
