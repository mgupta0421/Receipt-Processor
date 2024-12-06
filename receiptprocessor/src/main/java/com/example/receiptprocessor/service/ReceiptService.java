package com.example.receiptprocessor.service;

import org.springframework.stereotype.Service;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.repository.ReceiptRepository;

@Service
public class ReceiptService {
    private final ReceiptRepository repository;

    public ReceiptService(ReceiptRepository repository){
        this.repository = repository;
    }

    public Receipt processReceipt(Receipt receipt){
        repository.saveReceipt(receipt);
        return receipt;


    }
    
}
