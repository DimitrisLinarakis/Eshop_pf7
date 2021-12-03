package com.pf7.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Products {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
