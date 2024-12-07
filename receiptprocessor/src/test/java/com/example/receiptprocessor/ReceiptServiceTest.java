package com.example.receiptprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.repository.ReceiptRepository;
import com.example.receiptprocessor.service.ReceiptService;


 class ReceiptServiceTest {
    
    @InjectMocks
    private ReceiptService receiptService;

    @Mock
    private ReceiptRepository receiptRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        receiptService = new ReceiptService(receiptRepository);
    }

    @Test
    void processReceiptTest() {
        Receipt receipt = createSampleReceipt();        
        assertEquals(receipt, receiptService.processReceipt(receipt));
    } 

    @Test
    void getPointsTest(){
        Mockito.when(receiptRepository.getPoints(Mockito.anyString())).thenReturn(28);
        assertEquals(28, receiptService.getPoints("1"));
    }

    @Test
    void processPointsTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        // Access the private method using Java Reflection
        Method privateMethod = ReceiptService.class.getDeclaredMethod("processPoints", Receipt.class);

        // Make the private method accessible
        privateMethod.setAccessible(true);
 
        Receipt receipt = createSampleReceipt();        
        assertEquals(28, (int) privateMethod.invoke(receiptService, receipt));
       
        receipt.setRetailer("@@");
        assertEquals(22, (int) privateMethod.invoke(receiptService, receipt));

        receipt.setItems(new ArrayList<>(receipt.getItems()));
        receipt.getItems().removeIf(item -> !(Arrays.asList(new String[]{"Emils Cheese Pizza", "   Klarbrunn 12-PK 12 FL OZ  "}).contains(item.getShortDescription())));   
        assertEquals(17, (int) privateMethod.invoke(receiptService, receipt));

        receipt.getItems().clear();
        assertEquals(6, (int) privateMethod.invoke(receiptService, receipt));

        receipt.setPurchaseDate("2022-01-02");
        assertEquals(0, (int) privateMethod.invoke(receiptService, receipt));
    }

    // method to create sample receipt
    private Receipt createSampleReceipt(){
        Receipt receipt = new Receipt();
        receipt.setId("12345");
        receipt.setRetailer("Target");
        receipt.setTotal("35.35");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");

        Receipt.Item item1 = new Receipt.Item();
        item1.setShortDescription("Mountain Dew 12PK");
        item1.setPrice("6.49");

        Receipt.Item item2 = new Receipt.Item();
        item2.setShortDescription("Emils Cheese Pizza");
        item2.setPrice("12.25");

        Receipt.Item item3 = new Receipt.Item();
        item3.setShortDescription("Knorr Creamy Chicken");
        item3.setPrice("1.26");

        Receipt.Item item4 = new Receipt.Item();
        item4.setShortDescription("Doritos Nacho Cheese");
        item4.setPrice("3.35");

        Receipt.Item item5 = new Receipt.Item();
        item5.setShortDescription("   Klarbrunn 12-PK 12 FL OZ  ");
        item5.setPrice("12.00");

        receipt.setItems((List.of(item1, item2, item3, item4, item5)));
        return receipt;
    }
}