
package com.erp.service.sales;

import com.erp.dto.sales.CustomerDTO;
import com.erp.entity.sales.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();
    Customer getById(Long id);
    void create(CustomerDTO dto);
    void update(CustomerDTO dto);
    void delete(Long id);
}
