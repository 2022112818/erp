package com.erp.mapper.product;

import com.erp.entity.product.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    
    @Select("SELECT * FROM product WHERE id = {id}")
    Product findById(@Param("id") Long id);

    @Select("SELECT * FROM product")
    List<Product> findAll();

    @Insert("INSERT INTO product (name, category, unit, price) VALUES (#{name}, #{category}, #{unit}, #{price})")
    void insert(Product obj);

    @Update("UPDATE product SET name=#{name}, category=#{category}, unit=#{unit}, price=#{price} WHERE id=#{id}")
    void update(Product obj);

    @Delete("DELETE FROM product WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
