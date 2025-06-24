
package com.erp.service.purchase.impl;

import com.erp.dto.purchase.PurchaseOrderDTO;
import com.erp.entity.purchase.PurchaseOrder;
import com.erp.mapper.purchase.PurchaseOrderMapper;
import com.erp.service.purchase.PurchaseOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    @Autowired
    private PurchaseOrderMapper mapper;

    @Override
    public List<PurchaseOrder> getAll() {
        return mapper.findAll();
    }
    @Override
    public PurchaseOrder getById(Long id) {
        return mapper.findById(id);
    }
    @Override
    public void create(PurchaseOrderDTO dto) {
        PurchaseOrder entity = new PurchaseOrder();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(PurchaseOrderDTO dto) {
        PurchaseOrder entity = new PurchaseOrder();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
