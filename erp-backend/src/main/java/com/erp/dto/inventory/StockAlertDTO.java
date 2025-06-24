
package com.erp.dto.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockAlertDTO {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Integer minThreshold;
    private Integer maxThreshold;
}
