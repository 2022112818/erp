package com.erp.mapper.inventory;

import com.erp.entity.inventory.StockAlert;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StockAlertMapper {
    
    @Select("SELECT * FROM stock_alert WHERE id = {id}")
    StockAlert findById(@Param("id") Long id);

    @Select("SELECT * FROM stock_alert")
    List<StockAlert> findAll();

    @Insert("INSERT INTO stock_alert (product_id, warehouse_id, min_threshold, max_threshold) VALUES (#{productId}, #{warehouseId}, #{minThreshold}, #{maxThreshold})")
    void insert(StockAlert obj);

    @Update("UPDATE stock_alert SET product_id=#{productId}, warehouse_id=#{warehouseId}, min_threshold=#{minThreshold}, max_threshold=#{maxThreshold} WHERE id=#{id}")
    void update(StockAlert obj);

    @Delete("DELETE FROM stock_alert WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
