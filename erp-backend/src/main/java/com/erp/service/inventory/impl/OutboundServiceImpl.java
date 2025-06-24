
package com.erp.service.inventory.impl;

import com.erp.dto.inventory.OutboundDTO;
import com.erp.entity.inventory.Outbound;
import com.erp.mapper.inventory.OutboundRecordMapper;
import com.erp.service.inventory.OutboundService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OutboundServiceImpl implements OutboundService {
    @Autowired
    private OutboundRecordMapper mapper;

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
        Outbound entity = new Outbound();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(OutboundDTO dto) {
        Outbound entity = new Outbound();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
