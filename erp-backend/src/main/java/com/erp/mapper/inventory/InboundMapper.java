package com.erp.mapper.inventory;

import com.erp.entity.inventory.Inbound;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InboundMapper {
    
    @Select("SELECT * FROM inbound_record WHERE id = {id}")
    Inbound findById(@Param("id") Long id);

    @Select("SELECT * FROM inbound_record")
    List<Inbound> findAll();

    @Insert("INSERT INTO inbound_record (product_name, warehouse_id, quantity, inbound_date, operator, remark) VALUES (#{productName}, #{warehouseId}, #{quantity}, #{inboundDate}, #{operator}, #{remark})")
    void insert(Inbound obj);
}
