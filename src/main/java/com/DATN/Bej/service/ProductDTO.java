package com.DATN.Bej.service;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private String image;
    private int originalPrice;
    private int discount;
    private int finalPrice;
    private List<String> specs;
    private String createDate;
}
