package com.erp.service.inventory;

import com.erp.dto.inventory.InventoryNoticeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InventoryNoticeService {
    List<InventoryNoticeDTO> list();
}
