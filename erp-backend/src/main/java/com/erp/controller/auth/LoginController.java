
package com.erp.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.erp.common.result.Result;
import com.erp.entity.auth.User;
import com.erp.mapper.auth.UserMapper;
import com.erp.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户登录模块")
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            String token = JwtUtil.generateToken(username);
            Result<String> result = Result.success(token);

            JSONObject json = new JSONObject();
            json.put("id", user.getId());
            json.put("role", user.getRole());
            String jsonString = json.toJSONString();
            result.setData(jsonString);

            return result;
        } else {
            return Result.failure("用户名或密码错误");
        }
    }
}
