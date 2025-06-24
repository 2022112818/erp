
package com.erp.dto.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryDTO {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Integer quantity;

    // getter and setter
}
