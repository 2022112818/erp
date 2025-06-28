
package com.erp.service.inventory;

import com.erp.dto.inventory.StockAlertDTO;
import com.erp.entity.inventory.StockAlert;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StockAlertService {
    List<StockAlert> getAll();
    StockAlert getById(Long id);
    void create(StockAlertDTO dto);
    void update(StockAlertDTO dto);
    void delete(Long id);
}
