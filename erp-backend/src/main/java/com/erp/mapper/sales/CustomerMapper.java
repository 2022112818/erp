package com.erp.mapper.sales;

import com.erp.entity.sales.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {
    
    @Select("SELECT * FROM customer WHERE id = {id}")
    Customer findById(@Param("id") Long id);

    @Select("SELECT * FROM customer")
    List<Customer> findAll();

    @Insert("INSERT INTO customer (name, address, contact_person, phone) VALUES (#{name}, #{address}, #{contactPerson}, #{phone})")
    void insert(Customer obj);

    @Update("UPDATE customer SET name=#{name}, address=#{address}, contact_person=#{contactPerson}, phone=#{phone} WHERE id=#{id}")
    void update(Customer obj);

    @Delete("DELETE FROM customer WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
