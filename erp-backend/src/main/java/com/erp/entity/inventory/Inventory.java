
package com.erp.entity.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Inventory {
    private Long id;
    private String productName;
    private Long warehouseId;
    private Integer quantity;
    private LocalDateTime updateTime;
}
