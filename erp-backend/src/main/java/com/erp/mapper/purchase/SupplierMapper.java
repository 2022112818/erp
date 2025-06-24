package com.erp.mapper.purchase;

import com.erp.entity.purchase.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SupplierMapper {
    
    @Select("SELECT * FROM supplier WHERE id = {id}")
    Supplier findById(@Param("id") Long id);

    @Select("SELECT * FROM supplier")
    List<Supplier> findAll();

    @Insert("INSERT INTO supplier (name, address, contact_person, phone, bank_account) VALUES (#{name}, #{address}, #{contactPerson}, #{phone}, #{bankAccount})")
    void insert(Supplier obj);

    @Update("UPDATE supplier SET name=#{name}, address=#{address}, contact_person=#{contactPerson}, phone=#{phone}, bank_account=#{bankAccount} WHERE id=#{id}")
    void update(Supplier obj);

    @Delete("DELETE FROM supplier WHERE id = #{id}")
    void delete(@Param("id") Long id);
}
