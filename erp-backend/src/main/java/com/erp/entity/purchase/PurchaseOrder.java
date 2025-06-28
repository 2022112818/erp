
package com.erp.entity.purchase;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PurchaseOrder {
    private Long id;
    private String supplierName;
    private LocalDate orderDate; // 预计到货时间
    private BigDecimal totalAmount;
    private String productName;
    private Integer quantity;
    private String orderStatus;
    private String rejectionReason;
}
