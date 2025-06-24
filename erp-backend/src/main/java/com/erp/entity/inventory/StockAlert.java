
package com.erp.entity.inventory;

import lombok.Data;

@Data
public class StockAlert {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Integer minThreshold;
    private Integer maxThreshold;
}
