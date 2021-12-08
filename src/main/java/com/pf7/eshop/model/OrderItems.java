package com.pf7.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItems {
    private Integer orderItemsId;
    private Integer productId;
    private Integer orderId;
    private Integer quantity;

}
