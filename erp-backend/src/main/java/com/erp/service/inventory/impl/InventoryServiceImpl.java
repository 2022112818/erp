
package com.erp.service.inventory.impl;

import com.erp.dto.inventory.InventoryDTO;
import com.erp.entity.inventory.Inventory;
import com.erp.mapper.inventory.InventoryMapper;
import com.erp.service.inventory.InventoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryMapper.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryMapper.findById(id);
    }

    @Override
    public void createInventory(InventoryDTO dto) {
        Inventory entity = new Inventory();
        BeanUtils.copyProperties(dto, entity);
        inventoryMapper.insert(entity);
    }

    @Override
    public void updateInventory(InventoryDTO dto) {
        Inventory entity = new Inventory();
        BeanUtils.copyProperties(dto, entity);
        inventoryMapper.update(entity);
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryMapper.delete(id);
    }
}
