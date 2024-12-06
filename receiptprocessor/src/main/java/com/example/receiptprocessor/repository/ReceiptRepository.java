package com.example.receiptprocessor.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.example.receiptprocessor.model.Receipt;

@Repository
public class ReceiptRepository {
    private final ConcurrentHashMap<String, Receipt> receiptStore = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> pointStore = new ConcurrentHashMap<>();

    public Receipt saveReceipt(Receipt receipt){
        receiptStore.put(receipt.getId(), receipt);
        return receipt;
    }

    public Receipt getReceipt(String id){
        return receiptStore.get(id);
    }

    public void savePoints(String id, int points){
        pointStore.put(id, points);

    }

    public Integer getPoints(String id){
        return pointStore.get(id);
    }
}
