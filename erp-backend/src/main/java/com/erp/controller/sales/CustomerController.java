
package com.erp.controller.sales;

import com.erp.common.result.Result;
import com.erp.dto.sales.CustomerDTO;
import com.erp.entity.sales.Customer;
import com.erp.service.sales.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "客户管理")
@RestController
@RequestMapping("/api/sales/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @Operation(summary = "获取所有客户管理")
    @GetMapping("/list")
    public Result<List<Customer>> listAll() {
        return Result.success(service.getAll());
    }

    @Operation(summary = "根据ID获取客户管理")
    @GetMapping("/<built-in function id>")
    public Result<Customer> getById(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @Operation(summary = "创建客户管理")
    @PostMapping("/create")
    public Result<String> create(@RequestBody CustomerDTO dto) {
        service.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新客户管理")
    @PutMapping("/update")
    public Result<String> update(@RequestBody CustomerDTO dto) {
        service.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除客户管理")
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success("删除成功");
    }
}
