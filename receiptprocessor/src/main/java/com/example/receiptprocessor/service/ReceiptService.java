package com.example.receiptprocessor.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.repository.ReceiptRepository;


@Service
public class ReceiptService {
    private final ReceiptRepository repository;

    public ReceiptService(ReceiptRepository repository){
        this.repository = repository;
    }

    // Calculate points and store in memory
    public Receipt processReceipt(Receipt receipt){
        int points = processPoints(receipt);
        repository.saveReceipt(receipt);
        repository.savePoints(receipt.getId(), points);
        return receipt;

    }

    // Retrieve the points from the memory for the provided id
    public int getPoints(String id){
        Integer points = repository.getPoints(id);
        return points == null ? 0 : points;
    }

    // Calculate the total points of the receipt
    private int processPoints(Receipt receipt){
        int points = 0;
        
        // Rule 1 - One point for every alphanumeric character in the retailer name.
        if(StringUtils.isNotBlank(receipt.getRetailer())) {
            points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();
        }
        // Rule 2 - 50 points if the total is a round dollar amount with no cents.
        double totalAmount = 0.0;
        try{
            totalAmount = Double.parseDouble(receipt.getTotal());
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("Invalid total amount" +receipt.getTotal(), e);
        }
        if(totalAmount % 1 == 0){
            points += 50;
        }
        // Rule 3 - 25 points if the total is a multiple of 0.25.
        if(totalAmount % 0.25 == 0){
            points += 25;
        }
        // Rule 4- 5 points for every two items on the receipt.
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5 -
        // If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. 
        // The result is the number of points earned.
        points += receipt.getItems()
                    .stream()
                    .filter(item -> StringUtils.isNotBlank(item.getShortDescription()) && (StringUtils.length(StringUtils.trim(item.getShortDescription()))  % 3 == 0)
                                    && StringUtils.isNotBlank(item.getPrice())) // Filter the shortDesc which are divisible by 3
                    .mapToInt(item -> (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2))
                    .sum();

        // Rule 6 - 6 points if the day in the purchase date is odd.
        String date = receipt.getPurchaseDate();
        if(StringUtils.isNotBlank(date) && date.matches("\\d{4}-\\d{2}-\\d{2}")){
            int day = Integer.parseInt(date.split("-")[2]);
            if (day % 2 != 0) {
                points += 6;
            }
        }

        // Rule 7 - 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        String time = receipt.getPurchaseTime();
        if(StringUtils.isNotBlank(time) && time.matches("\\d{2}:\\d{2}")){
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
