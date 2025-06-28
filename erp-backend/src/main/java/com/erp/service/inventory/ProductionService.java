package com.erp.service.inventory;

import com.erp.dto.inventory.ProductionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductionService {
    List<ProductionDTO> getAll();
    ProductionDTO getById(Long id);
    void create(ProductionDTO dto);
}
