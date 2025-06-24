
package com.erp.service.sales;

import com.erp.dto.sales.SalesOrderDTO;
import com.erp.entity.sales.SalesOrder;
import java.util.List;

public interface SalesOrderService {
    List<SalesOrder> getAll();
    SalesOrder getById(Long id);
    void create(SalesOrderDTO dto);
    void update(SalesOrderDTO dto);
    void delete(Long id);
}
