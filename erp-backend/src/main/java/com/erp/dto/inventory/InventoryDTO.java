
package com.erp.dto.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryDTO {
    private Long id;
    private String productName;
    private Long warehouseId;
    private Integer quantity;
}
