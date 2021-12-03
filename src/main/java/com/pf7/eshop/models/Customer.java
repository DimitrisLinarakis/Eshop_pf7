package com.pf7.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Customer {
    private Integer customerID;
    private CustomerCategory customerCategory;
    private String username;
    private String password;
    private Integer orderID;

}
