
package com.erp.service.auth;

import com.erp.dto.auth.UserDTO;
import com.erp.entity.auth.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    void create(UserDTO dto);
    void update(UserDTO dto);
    void delete(Long id);
}
