
package com.erp.entity.inventory;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Inbound {
    private Long id;
    private Long purchaseOrderId;
    private Long warehouseId;
    private Integer quantity;
    private LocalDateTime inboundDate;
    private String operator;
    private String remark;
}
