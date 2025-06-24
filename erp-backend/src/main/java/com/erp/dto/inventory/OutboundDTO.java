
package com.erp.dto.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OutboundDTO {
    private Long id;
    private Long salesOrderId;
    private Long warehouseId;
    private Integer quantity;
    private LocalDateTime outboundDate;
    private String operator;
    private String remark;
}
