package com.pf7.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Customer {
    private Integer customerID;
    private CustomerCategory customerCategory;
    private String name;
    private String surname;
    private String email;

}
