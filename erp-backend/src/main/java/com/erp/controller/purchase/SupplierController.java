
package com.erp.controller.purchase;

import com.erp.common.result.Result;
import com.erp.dto.purchase.SupplierDTO;
import com.erp.entity.purchase.Supplier;
import com.erp.service.purchase.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "供应商管理")
@RestController
@RequestMapping("/api/purchase/supplier")
public class SupplierController {
    @Autowired
    private SupplierService service;

    @Operation(summary = "获取所有供应商管理")
    @GetMapping("/list")
    public Result<List<Supplier>> listAll() {
        return Result.success(service.getAll());
    }

    @Operation(summary = "根据ID获取供应商管理")
    @GetMapping("/<built-in function id>")
    public Result<Supplier> getById(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @Operation(summary = "创建供应商管理")
    @PostMapping("/create")
    public Result<String> create(@RequestBody SupplierDTO dto) {
        service.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新供应商管理")
    @PutMapping("/update")
    public Result<String> update(@RequestBody SupplierDTO dto) {
        service.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除供应商管理")
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success("删除成功");
    }
}
