package com.erp.dto.inventory;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductionDTO {
    // 来自 ProductionRecord
    private Long id;
    private String productName;
    private Long productWarehouseId;
    private Integer productQuantity;
    private LocalDateTime productionTime;
    private String operatorName;
    private String remark;

    // 关联的原材料列表
    private List<MaterialDTO> materials;

    @Data
    public static class MaterialDTO {
        private String materialName;
        private Long materialWarehouseId;
        private Integer usedQuantity;
    }
}