
package com.erp.controller.sales;

import com.erp.common.result.Result;
import com.erp.dto.sales.SalesOrderDTO;
import com.erp.entity.sales.SalesOrder;
import com.erp.service.sales.SalesOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "销售订单管理")
@RestController
@RequestMapping("/api/sales/salesOrder")
public class SalesOrderController {
    @Autowired
    private SalesOrderService service;

    @Operation(summary = "获取所有销售订单管理")
    @GetMapping("/list")
    public Result<List<SalesOrder>> listAll() {
        return Result.success(service.getAll());
    }

    @Operation(summary = "根据ID获取销售订单管理")
    @GetMapping("/<built-in function id>")
    public Result<SalesOrder> getById(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @Operation(summary = "创建销售订单管理")
    @PostMapping("/create")
    @Transactional
    public Result<String> create(@RequestBody SalesOrderDTO dto) {
        service.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新销售订单管理")
    @PutMapping("/update")
    @Transactional
    public Result<String> update(@RequestBody SalesOrderDTO dto) {
        service.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除销售订单管理")
    @DeleteMapping("/delete/{id}")
    @Transactional
    public Result<String> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success("删除成功");
    }
}
