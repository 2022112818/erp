
package com.erp.controller.auth;

import com.erp.common.result.Result;
import com.erp.dto.auth.UserDTO;
import com.erp.entity.auth.User;
import com.erp.service.auth.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/auth/user")
public class UserController {
    @Autowired
    private UserService service;

    @Operation(summary = "获取所有用户")
    @GetMapping("/list")
    public Result<List<User>> listAll() {
        return Result.success(service.getAll());
    }

    @Operation(summary = "根据ID获取用户")
    @GetMapping("/<built-in function id>")
    public Result<User> getById(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @Operation(summary = "创建用户")
    @PostMapping("/create")
    public Result<String> create(@RequestBody UserDTO dto) {
        service.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新用户")
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserDTO dto) {
        service.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success("删除成功");
    }
}
