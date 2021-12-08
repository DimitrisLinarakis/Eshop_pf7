package com.pf7.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {
    private Integer orderId;
    //private String productsId;
    private Integer customerId;
    private PaymentMethod paymentMethod;
    private BigDecimal totalPrice;
}
