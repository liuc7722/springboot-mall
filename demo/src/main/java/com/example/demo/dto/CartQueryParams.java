package com.example.demo.dto;

import lombok.Data;

@Data
public class CartQueryParams {
    Integer userId;
    Integer limit;
    Integer offset;
}
