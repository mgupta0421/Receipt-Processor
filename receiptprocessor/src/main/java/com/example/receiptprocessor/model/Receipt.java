package com.example.receiptprocessor.model;

import java.util.List;

import lombok.Data;

@Data
public class Receipt {
    private String id;
    private String retailer;
    private String purchasedDate;
    private String purchasedTime;
    private List<Item> items;
    private String total;

    @Data
    public static class Item {
        private String description;
        private String price;
    }
    
}
