package com.erp.mapper.inventory;

import com.erp.dto.inventory.ProductionDTO;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProductionMapper {

    @Select("SELECT * FROM production_record WHERE id = #{id}")
    ProductionDTO findById(@Param("id") Long id);

    @Select("SELECT * FROM production_record")
    List<ProductionDTO> findAll();

    @Insert("INSERT INTO production_record (product_name, product_warehouse_id, product_quantity, production_time, operator_name, remark) VALUES (#{productName}, #{productWarehouseId}, #{productQuantity}, #{productionTime}, #{operatorName}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertProductionRecord(ProductionDTO dto);

    @Insert({
            "<script>",
            "INSERT INTO production_material (production_record_id, material_name, material_warehouse_id, used_quantity) VALUES ",
            "<foreach collection='materials' item='material' separator=','>",
            "(#{productionRecordId}, #{material.materialName}, #{material.materialWarehouseId}, #{material.usedQuantity})",
            "</foreach>",
            "</script>"
    })
    void insertMaterials(@Param("productionRecordId") Long productionRecordId,
                         @Param("materials") List<ProductionDTO.MaterialDTO> materials);

    @Select("SELECT material_name as materialName, material_warehouse_id as materialWarehouseId, used_quantity as usedQuantity FROM production_material WHERE production_record_id = #{productionRecordId}")
    List<ProductionDTO.MaterialDTO> findMaterialsByProductionId(@Param("productionRecordId") Long productionRecordId);
}