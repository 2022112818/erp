
package com.erp.entity.sales;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalesOrder {
    private Long id;
    private Long customerId;
    private String productName;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String salesperson;
}
