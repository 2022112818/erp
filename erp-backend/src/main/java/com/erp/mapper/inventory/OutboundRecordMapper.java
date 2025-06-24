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

    @Insert("INSERT INTO outbound_record (sales_orderId, warehouse_id, quantity, outbound_date, operator, remark) VALUES (#{salesOrderId}, #{warehouseId}, #{quantity}, #{outboundDate}, #{operator}, #{remark})")
    void insert(Outbound obj);

    @Update("UPDATE outbound_record SET sales_order_id=#{salesOrderId}, warehouse_id=#{warehouseId}, quantity=#{quantity}, outbound_date=#{outboundDate}, operator=#{operator}, remark=#{remark} WHERE id=#{id}")
    void update(Outbound obj);

    @Delete("DELETE FROM outbound_record WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
