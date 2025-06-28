
package com.erp.service.purchase;

import com.erp.dto.purchase.SupplierDTO;
import com.erp.entity.purchase.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAll();
    Supplier getById(Long id);
    void create(SupplierDTO dto);
    void update(SupplierDTO dto);
    void delete(Long id);
}
