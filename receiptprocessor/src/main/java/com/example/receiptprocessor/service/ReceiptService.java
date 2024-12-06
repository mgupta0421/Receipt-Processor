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
        int points = processPoints(receipt);
        repository.saveReceipt(receipt);
        repository.savePoints(receipt.getId(), points);
        return receipt;

    }

    public int getPoints(String id){
        Integer points = repository.getPoints(id);
        return points == null ? 0 : points;
    }

    public int processPoints(Receipt receipt){
        int points = 0;

        // Condition 1
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Condition 2
        double totalAmount = Double.parseDouble(receipt.getTotal());
        if(totalAmount % 1 == 0){
            points += 50;
        }
        // Condition 3
        if(totalAmount % 0.25 == 0){
            points += 25;
        }
        // Condition 4
        points += (receipt.getItems().size() / 2) * 5;

        // Condition 5
        for(Receipt.Item item : receipt.getItems()){
            String desc = item.getShortDescription();
            if(desc != null && item.getPrice() != null){
                String modifiedDesc = desc.trim();
                if(modifiedDesc.length() % 3 == 0){
                    points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
                }
            }
            
        }
        // Condition 6
        String date = receipt.getPurchaseDate();
        if(date != null){
            int day = Integer.parseInt(date.split("-")[2]);
            if (day % 2 != 0) {
                points += 6;
            }
        }
        
        // Condition 7
        String time = receipt.getPurchaseTime();
        if(time != null){
            String[] parts = receipt.getPurchaseTime().split(":");
            int hour = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            if(hour == 14 || (hour == 15 && minutes == 0)){
                points += 10;
            }
        }
        return points;
    }
    
}
