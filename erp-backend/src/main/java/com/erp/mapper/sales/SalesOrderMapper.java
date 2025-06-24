package com.erp.mapper.sales;

import com.erp.entity.sales.SalesOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SalesOrderMapper {
    
    @Select("SELECT * FROM sales_order WHERE id = {id}")
    SalesOrder findById(@Param("id") Long id);

    @Select("SELECT * FROM sales_order")
    List<SalesOrder> findAll();

    @Insert("INSERT INTO sales_order (customer_id, order_date, total_amount) VALUES (#{customerId}, #{orderDate}, #{totalAmount})")
    void insert(SalesOrder obj);

    @Update("UPDATE sales_order SET customer_id=#{customerId}, order_date=#{orderDate}, total_amount=#{totalAmount} WHERE id=#{id}")
    void update(SalesOrder obj);

    @Delete("DELETE FROM sales_order WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
