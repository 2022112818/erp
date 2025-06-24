
package com.erp.service.auth;

import com.erp.dto.auth.UserDTO;
import com.erp.entity.auth.User;
import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    void create(UserDTO dto);
    void update(UserDTO dto);
    void delete(Long id);
}
