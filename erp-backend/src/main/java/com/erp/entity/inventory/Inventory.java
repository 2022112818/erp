
package com.erp.entity.inventory;

import lombok.Data;

@Data
public class Inventory {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Integer quantity;
}
