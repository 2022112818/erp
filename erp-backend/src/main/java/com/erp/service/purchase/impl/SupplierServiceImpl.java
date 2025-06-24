
package com.erp.service.purchase.impl;

import com.erp.dto.purchase.SupplierDTO;
import com.erp.entity.purchase.Supplier;
import com.erp.mapper.purchase.SupplierMapper;
import com.erp.service.purchase.SupplierService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierMapper mapper;

    @Override
    public List<Supplier> getAll() {
        return mapper.findAll();
    }
    @Override
    public Supplier getById(Long id) {
        return mapper.findById(id);
    }
    @Override
    public void create(SupplierDTO dto) {
        Supplier entity = new Supplier();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(SupplierDTO dto) {
        Supplier entity = new Supplier();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
