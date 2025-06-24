
package com.erp.entity.inventory;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Outbound {
    private Long id;
    private Long salesOrderId;
    private Long warehouseId;
    private Integer quantity;
    private LocalDateTime outboundDate;
    private String operator;
    private String remark;
}
