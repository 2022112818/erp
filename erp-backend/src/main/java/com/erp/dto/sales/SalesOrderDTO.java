
package com.erp.dto.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SalesOrderDTO {
    private Long id;
    private Long customerId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
}
