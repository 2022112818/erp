package com.erp.mapper.inventory;

import com.erp.entity.inventory.Warehouse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseMapper {
    
    @Select("SELECT * FROM warehouse WHERE id = {id}")
    Warehouse findById(@Param("id") Long id);

    @Select("SELECT * FROM warehouse")
    List<Warehouse> findAll();

    @Insert("INSERT INTO warehouse (name, location, manager, contact) VALUES (#{name}, #{location}, #{manager}, #{contact})")
    void insert(Warehouse obj);

    @Update("UPDATE warehouse SET name=#{name}, location=#{location}, manager=#{manager}, contact=#{contact} WHERE id=#{id}")
    void update(Warehouse obj);

    @Delete("DELETE FROM warehouse WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
