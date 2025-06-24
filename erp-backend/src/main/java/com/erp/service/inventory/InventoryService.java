
package com.erp.service.inventory;

import com.erp.dto.inventory.InventoryDTO;
import com.erp.entity.inventory.Inventory;
import java.util.List;

public interface InventoryService {
    List<Inventory> getAllInventory();
    Inventory getInventoryById(Long id);
    void createInventory(InventoryDTO dto);
    void updateInventory(InventoryDTO dto);
    void deleteInventory(Long id);
}
