package com.erp.mapper.purchase;

import com.erp.entity.purchase.PurchaseOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PurchaseOrderMapper {
    
    @Select("SELECT * FROM purchase_order WHERE id = {id}")
    PurchaseOrder findById(@Param("id") Long id);

    @Select("SELECT * FROM purchase_order")
    List<PurchaseOrder> findAll();

    @Insert("INSERT INTO purchase_order (supplier_id, orderDate, total_amount) VALUES (#{supplierId}, #{orderDate}, #{totalAmount})")
    void insert(PurchaseOrder obj);

    @Update("UPDATE purchase_order SET supplier_id=#{supplierId}, order_date=#{orderDate}, total_amount=#{totalAmount} WHERE id=#{id}")
    void update(PurchaseOrder obj);

    @Delete("DELETE FROM purchase_order WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
