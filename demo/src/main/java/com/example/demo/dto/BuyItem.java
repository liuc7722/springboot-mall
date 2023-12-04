package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuyItem {
    @NotNull
    Integer productId;
    @NotNull
    Integer quantity;
}
