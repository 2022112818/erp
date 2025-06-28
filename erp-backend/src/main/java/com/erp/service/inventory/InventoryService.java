
package com.erp.service.inventory;

import com.erp.entity.inventory.Inventory;

import java.util.List;

public interface InventoryService {
    List<Inventory> getAllInventory();
    Inventory getInventoryById(Long id);
    void changeInventory(Long warehouseId, String productName, Integer delta); // positive or negative
}
