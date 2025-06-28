package com.erp.entity.inventory;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductionRecord {
    private Long id;
    private String productName;
    private Long productWarehouseId;
    private Integer productQuantity;
    private LocalDateTime productionTime;
    private String operatorName;
    private String remark;
}
