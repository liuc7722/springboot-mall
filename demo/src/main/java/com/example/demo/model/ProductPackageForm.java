package com.example.demo.model;

import java.util.List;

import lombok.Data;


@Data
public class ProductPackageForm {

    String id; // 產品id(好像不重要)

    String name; // 產品名稱(好像不重要)

    Integer amount; // 產品數量(好像不重要)

    

    List<ProductForm> products;

    
}