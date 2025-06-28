
package com.erp.service.inventory.impl;

import com.erp.dto.inventory.InboundDTO;
import com.erp.entity.inventory.Inbound;
import com.erp.mapper.inventory.InboundMapper;
import com.erp.service.inventory.InboundService;
import com.erp.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InboundServiceImpl implements InboundService {

    @Autowired
    private InboundMapper mapper;
    @Autowired
    private InventoryService service;

    @Override
    public List<Inbound> getAll() {
        return mapper.findAll();
    }

    @Override
    public Inbound getById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public void create(InboundDTO dto) {
        if (dto.getProductName() == null
                || dto.getWarehouseId() == null
                || dto.getQuantity() == null
                || dto.getQuantity() <= 0) {
            throw new RuntimeException();
        }
        Inbound entity = new Inbound(null, dto.getProductName(), dto.getWarehouseId(), dto.getQuantity(),
                LocalDateTime.now(), "san", dto.getRemark());
        mapper.insert(entity);
        service.changeInventory(dto.getWarehouseId(), dto.getProductName(), dto.getQuantity());
    }
}
