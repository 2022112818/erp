
package com.erp.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private String unit;
    private BigDecimal price;
}
