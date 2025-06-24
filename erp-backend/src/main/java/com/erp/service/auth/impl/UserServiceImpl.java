
package com.erp.service.auth.impl;

import com.erp.dto.auth.UserDTO;
import com.erp.entity.auth.User;
import com.erp.mapper.auth.UserMapper;
import com.erp.service.auth.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> getAll() {
        return mapper.findAll();
    }
    @Override
    public User getById(Long id) {
        return mapper.findById(id);
    }
    @Override
    public void create(UserDTO dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        mapper.insert(entity);
    }
    @Override
    public void update(UserDTO dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        mapper.update(entity);
    }
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
}
