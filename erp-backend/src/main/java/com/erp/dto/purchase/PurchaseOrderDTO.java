
package com.erp.dto.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PurchaseOrderDTO {
    private Long id;
    private Long supplierId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
}
