
package com.erp.service.sales.impl;

import com.erp.dto.sales.CustomerDTO;
import com.erp.entity.sales.Customer;
import com.erp.mapper.sales.CustomerMapper;
import com.erp.service.sales.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper mapper;

    @Override
    public List<Customer> getAll() {
        return mapper.findAll();
    }
    @Override
    public Customer getById(Long id) {
        return mapper.findById(id);
    }
    @Override
    public void create(CustomerDTO dto) {
        Customer entity = new Customer();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(CustomerDTO dto) {
        Customer entity = new Customer();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
