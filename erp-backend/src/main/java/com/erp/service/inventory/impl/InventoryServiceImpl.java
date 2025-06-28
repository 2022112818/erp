
package com.erp.service.inventory.impl;

import com.erp.entity.inventory.Inventory;
import com.erp.mapper.inventory.InventoryMapper;
import com.erp.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void changeInventory(Long warehouseId, String productName, Integer delta) {
        List<Inventory> list = inventoryMapper.search(warehouseId, productName);
        if (list.isEmpty()) {
            if (delta <= 0) {
                throw new RuntimeException();
            }
            Inventory inventory = new Inventory(null, productName, warehouseId, delta, LocalDateTime.now());
            inventoryMapper.insert(inventory);
        } else {
            if (list.size() != 1 || list.get(0).getId() == null) {
                throw new RuntimeException();
            }
            if (delta >= 0 || list.get(0).getQuantity() + delta >= 0) {
                inventoryMapper.update(list.get(0).getId(), delta, LocalDateTime.now());
            } else {
                throw new RuntimeException();
            }
        }
    }
}
