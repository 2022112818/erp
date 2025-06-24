
package com.erp.service.inventory.impl;

import com.erp.dto.inventory.WarehouseDTO;
import com.erp.entity.inventory.Warehouse;
import com.erp.mapper.inventory.WarehouseMapper;
import com.erp.service.inventory.WarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseMapper mapper;

    @Override
    public List<Warehouse> getAll() {
        return mapper.findAll();
    }
    @Override
    public Warehouse getById(Long id) {
        return mapper.findById(id);
    }
    @Override
    public void create(WarehouseDTO dto) {
        Warehouse entity = new Warehouse();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(WarehouseDTO dto) {
        Warehouse entity = new Warehouse();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
