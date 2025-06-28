
package com.erp.service.inventory.impl;

import com.erp.dto.inventory.OutboundDTO;
import com.erp.entity.inventory.Outbound;
import com.erp.mapper.inventory.OutboundRecordMapper;
import com.erp.service.inventory.InventoryService;
import com.erp.service.inventory.OutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutboundServiceImpl implements OutboundService {
    @Autowired
    private OutboundRecordMapper mapper;
    @Autowired
    private InventoryService service;

    @Override
    public List<Outbound> getAll() {
        return mapper.findAll();
    }

    @Override
    public Outbound getById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public void create(OutboundDTO dto) {
        if (dto.getProductName() == null
                || dto.getWarehouseId() == null
                || dto.getQuantity() == null
                || dto.getQuantity() <= 0) {
            throw new RuntimeException();
        }
        Outbound entity = new Outbound(null, dto.getProductName(), dto.getWarehouseId(), dto.getQuantity(),
                LocalDateTime.now(), "张三", dto.getRemark());
        mapper.insert(entity);
        service.changeInventory(dto.getWarehouseId(), dto.getProductName(), -dto.getQuantity());
    }
}
