package com.erp.mapper.inventory;

import com.erp.entity.inventory.Inventory;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface InventoryMapper {
    
    @Select("SELECT * FROM inventory WHERE id = {id}")
    Inventory findById(@Param("id") Long id);

    @Select("SELECT * FROM inventory")
    List<Inventory> findAll();

    @Insert("INSERT INTO inventory (product_name, warehouse_id, quantity, update_time) VALUES (#{productName}, #{warehouseId}, #{quantity}, #{updateTime})")
    void insert(Inventory obj);

    @Update("UPDATE inventory SET quantity = quantity + #{delta}, update_time = #{updateTime} WHERE id=#{id}")
    void update(@Param("id") Long id, @Param("delta") Integer delta, @Param("updateTime") LocalDateTime updateTime);

    @Select("SELECT * FROM inventory WHERE warehouse_id = #{warehouseId} AND product_name = #{productName}")
    List<Inventory> search(@Param("warehouseId") Long warehouseId, @Param("productName") String productName);
}
