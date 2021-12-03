package com.pf7.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OrderItems {
    private Integer orderItemsID;
    private Integer productID;
    private Integer orderId;
    private Integer quantity;


}
