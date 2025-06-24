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

    @Insert("INSERT INTO inbound_record (purchase_order_id, warehouse_id, quantity, inboundDate, operator, remark) VALUES (#{purchaseOrderId}, #{warehouseId}, #{quantity}, #{inboundDate}, #{operator}, #{remark})")
    void insert(Inbound obj);

    @Update("UPDATE inbound_record SET purchase_order_id=#{purchaseOrderId}, warehouse_id=#{warehouseId}, quantity=#{quantity}, inboundDate=#{inboundDate}, operator=#{operator}, remark=#{remark} WHERE id=#{id}")
    void update(Inbound obj);

    @Delete("DELETE FROM inbound_record WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
