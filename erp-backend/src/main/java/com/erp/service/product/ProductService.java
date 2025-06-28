
package com.erp.service.product;

import com.erp.dto.product.ProductDTO;
import com.erp.entity.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product getById(Long id);
    void create(ProductDTO dto);
    void update(ProductDTO dto);
    void delete(Long id);
}
