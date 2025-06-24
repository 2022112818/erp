
package com.erp.controller.inventory;

import com.erp.common.result.Result;
import com.erp.dto.inventory.WarehouseDTO;
import com.erp.entity.inventory.Warehouse;
import com.erp.service.inventory.WarehouseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "仓库管理")
@RestController
@RequestMapping("/api/inventory/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @Operation(summary = "获取所有仓库管理")
    @GetMapping("/list")
    public Result<List<Warehouse>> listAll() {
        return Result.success(warehouseService.getAll());
    }

    @Operation(summary = "根据ID获取仓库管理")
    @GetMapping("/<built-in function id>")
    public Result<Warehouse> getById(@PathVariable Long id) {
        return Result.success(warehouseService.getById(id));
    }

    @Operation(summary = "创建仓库管理")
    @PostMapping("/create")
    public Result<String> create(@RequestBody WarehouseDTO dto) {
        warehouseService.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新仓库管理")
    @PutMapping("/update")
    public Result<String> update(@RequestBody WarehouseDTO dto) {
        warehouseService.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除仓库管理")
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return Result.success("删除成功");
    }
}
