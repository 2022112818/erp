
package com.erp.service.inventory.impl;

import com.erp.dto.inventory.StockAlertDTO;
import com.erp.entity.inventory.StockAlert;
import com.erp.mapper.inventory.StockAlertMapper;
import com.erp.service.inventory.StockAlertService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StockAlertServiceImpl implements StockAlertService {
    @Autowired
    private StockAlertMapper mapper;

    @Override
    public List<StockAlert> getAll() {
        return mapper.findAll();
    }
    @Override
    public StockAlert getById(Long id) {
        return mapper.findById(id);
    }
    @Override
    public void create(StockAlertDTO dto) {
        StockAlert entity = new StockAlert();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(StockAlertDTO dto) {
        StockAlert entity = new StockAlert();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
