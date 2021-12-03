package com.pf7.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {
    private Integer orderItemsID;
    private Integer productID;
    private Integer orderId;
    private Integer quantity;


}
