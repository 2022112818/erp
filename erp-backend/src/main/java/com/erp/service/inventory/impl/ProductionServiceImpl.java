package com.erp.service.inventory.impl;

import com.erp.dto.inventory.ProductionDTO;
import com.erp.mapper.inventory.ProductionMapper;
import com.erp.service.inventory.InventoryService;
import com.erp.service.inventory.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductionServiceImpl implements ProductionService {
    @Autowired
    private ProductionMapper mapper;
    @Autowired
    private InventoryService service;

    @Override
    public List<ProductionDTO> getAll() {
        List<ProductionDTO> res = mapper.findAll();
        for (ProductionDTO dto : res) {
            dto.setMaterials(mapper.findMaterialsByProductionId(dto.getId()));
        }
        return res;
    }

    @Override
    public ProductionDTO getById(Long id) {
        ProductionDTO dto = mapper.findById(id);
        List<ProductionDTO.MaterialDTO> list = mapper.findMaterialsByProductionId(dto.getId());
        dto.setMaterials(list);
        return dto;
    }

    @Override
    public void create(ProductionDTO dto) {
        dto.setProductionTime(LocalDateTime.now());
        mapper.insertProductionRecord(dto);
        service.changeInventory(dto.getProductWarehouseId(), dto.getProductName(), dto.getProductQuantity());
        if (dto.getMaterials() != null && !dto.getMaterials().isEmpty()) {
            mapper.insertMaterials(dto.getId(), dto.getMaterials());
            for (ProductionDTO.MaterialDTO material : dto.getMaterials()) {
                service.changeInventory(material.getMaterialWarehouseId(), material.getMaterialName(), -material.getUsedQuantity());
            }
        }
    }
}
