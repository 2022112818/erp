package com.erp.service.inventory.impl;

import com.erp.dto.inventory.InventoryNoticeDTO;
import com.erp.mapper.inventory.InventoryNoticeMapper;
import com.erp.service.inventory.InventoryNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryNoticeServiceImpl implements InventoryNoticeService {
    @Autowired
    InventoryNoticeMapper mapper;

    @Override
    public List<InventoryNoticeDTO> list() {
        List<InventoryNoticeDTO> list = new ArrayList<>();
        for (InventoryNoticeDTO dto : mapper.futureInbound(LocalDate.now())) {
            dto.setNoticeType("入库");
            list.add(dto);
        }
        for (InventoryNoticeDTO dto : mapper.futureOutbound(LocalDate.now())) {
            dto.setNoticeType("出库");
            list.add(dto);
        }
        return list;
    }
}
