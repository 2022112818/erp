
package com.erp.service.sales.impl;

import com.erp.dto.sales.SalesOrderDTO;
import com.erp.entity.sales.SalesOrder;
import com.erp.mapper.sales.SalesOrderMapper;
import com.erp.service.sales.SalesOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {
    @Autowired
    private SalesOrderMapper mapper;

    @Override
    public List<SalesOrder> getAll() {
        return mapper.findAll();
    }
    @Override
    public SalesOrder getById(Long id) {
        return mapper.findById(id);
    }
    @Override
    public void create(SalesOrderDTO dto) {
        SalesOrder entity = new SalesOrder();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(SalesOrderDTO dto) {
        SalesOrder entity = new SalesOrder();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
