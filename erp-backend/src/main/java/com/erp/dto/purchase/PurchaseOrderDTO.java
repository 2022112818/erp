
package com.erp.dto.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PurchaseOrderDTO {
    private Long id;
    private String supplierName;
    private LocalDate orderDate; // 预计到货时间
    private BigDecimal totalAmount;
    private String productName;
    private Integer quantity;
    private String orderStatus;
    private String rejectionReason;
}
