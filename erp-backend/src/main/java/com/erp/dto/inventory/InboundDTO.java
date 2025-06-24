
package com.erp.dto.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InboundDTO {
    private Long id;
    private Long purchaseOrderId;
    private Long warehouseId;
    private Integer quantity;
    private LocalDateTime inboundDate;
    private String operator;
    private String remark;
}
