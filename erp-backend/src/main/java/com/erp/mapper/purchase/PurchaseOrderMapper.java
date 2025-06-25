package com.erp.mapper.purchase;

import com.erp.entity.purchase.PurchaseOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PurchaseOrderMapper {

    @Select("SELECT * FROM purchase_order WHERE id = #{id}")
    PurchaseOrder findById(@Param("id") Long id);

    @Select("SELECT * FROM purchase_order")
    List<PurchaseOrder> findAll();

    @Insert("INSERT INTO purchase_order (supplier_name, product_name, quantity, order_date, total_amount, order_status, rejection_reason) " +
            "VALUES (#{supplierName}, #{productName}, #{quantity}, #{orderDate}, #{totalAmount}, #{orderStatus}, #{rejectionReason})")
    void insert(PurchaseOrder obj);

    @Update("UPDATE purchase_order SET supplier_name=#{supplierName}, product_name=#{productName}, quantity=#{quantity}, " +
            "order_date=#{orderDate}, total_amount=#{totalAmount}, order_status=#{orderStatus}, rejection_reason=#{rejectionReason} " +
            "WHERE id=#{id}")
    void update(PurchaseOrder obj);

    @Delete("DELETE FROM purchase_order WHERE id = #{id}")
    void delete(@Param("id") Long id);
}