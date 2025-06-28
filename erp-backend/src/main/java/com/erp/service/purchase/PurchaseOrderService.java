
package com.erp.service.purchase;

import com.erp.dto.purchase.PurchaseOrderDTO;
import com.erp.entity.purchase.PurchaseOrder;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PurchaseOrderService {
    List<PurchaseOrder> getAll();
    PurchaseOrder getById(Long id);
    void create(PurchaseOrderDTO dto);
    void update(PurchaseOrderDTO dto);
    void delete(Long id);
    void approve(Long id);
    void reject(Long id, String reason);
    void pay(Long id);
}
