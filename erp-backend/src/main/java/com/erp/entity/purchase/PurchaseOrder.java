
package com.erp.entity.purchase;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PurchaseOrder {
    private Long id;
    private Long supplierId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
}
