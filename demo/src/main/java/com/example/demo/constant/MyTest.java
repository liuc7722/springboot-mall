package com.example.demo.constant;

public class MyTest {
    public static void main(String[] args) {
        // 將enum和String作轉換
        ProductCategory productCategory = ProductCategory.FOOD;
        String s = productCategory.name();
        System.out.println(s); //FOOD

        String s2 = "CAR";
        ProductCategory productCategory2 = ProductCategory.valueOf(s2);
    }
}
