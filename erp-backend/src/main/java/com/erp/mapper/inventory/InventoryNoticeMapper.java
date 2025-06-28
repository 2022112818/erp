package com.erp.mapper.inventory;

import com.erp.dto.inventory.InventoryNoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface InventoryNoticeMapper {
    @Select("SELECT product_name as productName, quantity, DATE(order_date) as date " +
            "FROM purchase_order " +
            "WHERE DATE(order_date) >= #{current} " +
            "ORDER BY order_date ASC")
    List<InventoryNoticeDTO> futureInbound(@Param("current") LocalDate current);

    @Select("SELECT product_name as productName, quantity, DATE(order_date) as date " +
            "FROM sales_order " +
            "WHERE DATE(order_date) >= #{current} " +
            "ORDER BY order_date ASC")
    List<InventoryNoticeDTO> futureOutbound(@Param("current") LocalDate current);
}
