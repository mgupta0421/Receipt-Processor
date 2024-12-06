package com.example.receiptprocessor.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Receipt {
    private String id;
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<Item> items;
    private String total;

    // random id generation
    public Receipt(){
        this.id = UUID.randomUUID().toString();
    }

    @Data
    public static class Item {
        private String shortDescription;
        private String price;
    }
    
}
