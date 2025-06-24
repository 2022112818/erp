package com.erp.mapper.inventory;

import com.erp.entity.inventory.Inventory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InventoryMapper {
    
    @Select("SELECT * FROM inventory WHERE id = {id}")
    Inventory findById(@Param("id") Long id);

    @Select("SELECT * FROM inventory")
    List<Inventory> findAll();

    @Insert("INSERT INTO inventory (product_id, warehouse_id, quantity) VALUES (#{productId}, #{warehouseId}, #{quantity})")
    void insert(Inventory obj);

    @Update("UPDATE inventory SET product_id=#{productId}, warehouse_id=#{warehouseId}, quantity=#{quantity} WHERE id=#{id}")
    void update(Inventory obj);

    @Delete("DELETE FROM inventory WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
