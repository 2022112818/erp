package com.erp.entity.inventory;

import lombok.Data;

@Data
public class ProductionMaterial {
    private Long id;
    private Long productionRecordId;
    private String materialName;
    private Long materialWarehouseId;
    private Integer usedQuantity;
}