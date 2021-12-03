package com.pf7.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Integer orderId;
    private String productsId;
    private Integer customerId;
    private PaymentMethod paymentMethod;
    private BigDecimal TotalPrice;
}
