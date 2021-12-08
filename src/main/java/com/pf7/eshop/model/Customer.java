package com.pf7.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
