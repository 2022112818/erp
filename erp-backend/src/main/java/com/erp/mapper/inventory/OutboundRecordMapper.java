package com.erp.mapper.inventory;

import com.erp.entity.inventory.Outbound;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OutboundRecordMapper {
    
    @Select("SELECT * FROM outbound_record WHERE id = {id}")
    Outbound findById(@Param("id") Long id);

    @Select("SELECT * FROM outbound_record")
    List<Outbound> findAll();

    @Insert("INSERT INTO outbound_record (product_name, warehouse_id, quantity, outbound_date, operator, remark) VALUES (#{productName}, #{warehouseId}, #{quantity}, #{outboundDate}, #{operator}, #{remark})")
    void insert(Outbound obj);

}
