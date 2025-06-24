
package com.erp.entity.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Long id;
    private String name;
    private String category;
    private String unit;
    private BigDecimal price;
}
