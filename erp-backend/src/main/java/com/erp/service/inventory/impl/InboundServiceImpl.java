
package com.erp.service.inventory.impl;

import com.erp.dto.inventory.InboundDTO;
import com.erp.entity.inventory.Inbound;
import com.erp.mapper.inventory.InboundMapper;
import com.erp.service.inventory.InboundService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InboundServiceImpl implements InboundService {

    @Autowired
    private InboundMapper mapper;

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
        Inbound entity = new Inbound();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }

    @Override
    public void update(InboundDTO dto) {
        Inbound entity = new Inbound();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }

    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
