
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
        } else {
            if (!entity.getOrderStatus().equals("PENDING")) {
                throw new RuntimeException("无法设置待审核之外的状态");
            }
        }

        mapper.insert(entity);
    }

    @Override
    public void update(PurchaseOrderDTO dto) {
        PurchaseOrder entity = new PurchaseOrder();
        BeanUtils.copyProperties(dto, entity);
        if (!StringUtils.hasText(entity.getOrderStatus())) {
            entity.setOrderStatus("PENDING");
        } else {
            if (!entity.getOrderStatus().equals("PENDING")) {
                throw new RuntimeException("无法设置待审核之外的状态");
            }
        }
        mapper.update(entity);
    }

    @Override
    public void delete(Long id) {
        // 只有待审核状态的订单才能删除
        PurchaseOrder order = mapper.findById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (!"PENDING".equals(order.getOrderStatus())) {
            throw new RuntimeException("只有待审核状态的订单才能删除");
        }

        mapper.delete(id);
    }

    @Override
    public void approve(Long id) {
        PurchaseOrder order = mapper.findById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (!"PENDING".equals(order.getOrderStatus())) {
            throw new RuntimeException("只有待审核状态的订单才能审核通过");
        }

        // 更新订单状态为已审核
        order.setOrderStatus("APPROVED");
        order.setRejectionReason(null); // 清空拒绝原因

        mapper.update(order);
    }

    @Override
    public void reject(Long id, String reason) {
        PurchaseOrder order = mapper.findById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (!"PENDING".equals(order.getOrderStatus())) {
            throw new RuntimeException("只有待审核状态的订单才能拒绝");
        }

        if (reason == null || reason.trim().isEmpty()) {
            throw new RuntimeException("拒绝原因不能为空");
        }

        // 更新订单状态为已拒绝，并设置拒绝原因
        order.setOrderStatus("REJECTED");
        order.setRejectionReason(reason);

        mapper.update(order);
    }

    @Override
    public void pay(Long id) {
        PurchaseOrder order = mapper.findById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (!"APPROVED".equals(order.getOrderStatus())) {
            throw new RuntimeException("只有已审核状态的订单才能支付");
        }

        // 更新订单状态为已完成
        order.setOrderStatus("COMPLETED");

        mapper.update(order);
    }
}