
package com.erp.service.product.impl;

import com.erp.dto.product.ProductDTO;
import com.erp.entity.product.Product;
import com.erp.mapper.product.ProductMapper;
import com.erp.service.product.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll() {
        return mapper.findAll();
    }
    @Override
    public Product getById(Long id) {
        return mapper.findById(id);
    }
    @Override
    public void create(ProductDTO dto) {
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(ProductDTO dto) {
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
