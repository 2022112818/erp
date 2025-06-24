
package com.erp.service.inventory;

import com.erp.dto.inventory.WarehouseDTO;
import com.erp.entity.inventory.Warehouse;
import java.util.List;

public interface WarehouseService {
    List<Warehouse> getAll();
    Warehouse getById(Long id);
    void create(WarehouseDTO dto);
    void update(WarehouseDTO dto);
    void delete(Long id);
}
