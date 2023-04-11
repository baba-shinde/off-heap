package com.bss.demo.offheap.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Order {
    private String orderId;
    private String micCode;
    private String ricCode;
    private long quantity;
    private double price;
    private Side side;
    private Date date;
}
