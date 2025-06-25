
package com.erp.service.purchase.impl;

import com.erp.dto.purchase.PurchaseOrderDTO;
import com.erp.entity.purchase.PurchaseOrder;
import com.erp.mapper.purchase.PurchaseOrderMapper;
import com.erp.service.purchase.PurchaseOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

        // 如果没有设置状态，默认设为待审核
        if (!StringUtils.hasText(entity.getOrderStatus())) {
            entity.setOrderStatus("PENDING");
        }

        mapper.insert(entity);
    }

    @Override
    public void update(PurchaseOrderDTO dto) {
        PurchaseOrder entity = new PurchaseOrder();
        BeanUtils.copyProperties(dto, entity);
        if (!StringUtils.hasText(entity.getOrderStatus())) {
            entity.setOrderStatus("PENDING");
        }
        mapper.update(entity);
    }

    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }

    @Override
    public void approve(Long id) {
        PurchaseOrder entity = mapper.findById(id);
        if (entity != null) {
            entity.setOrderStatus("APPROVED");
            entity.setRejectionReason(null); // 清空拒绝原因
            mapper.update(entity);
        }
    }

    @Override
    public void reject(Long id, String reason) {
        PurchaseOrder entity = mapper.findById(id);
        if (entity != null) {
            entity.setOrderStatus("REJECTED");
            entity.setRejectionReason(reason);
            mapper.update(entity);
        }
    }

    @Override
    public void pay(Long id) {
        PurchaseOrder entity = mapper.findById(id);
        if (entity != null) {
            entity.setOrderStatus("COMPLETED");
            mapper.update(entity);
        }
    }
}