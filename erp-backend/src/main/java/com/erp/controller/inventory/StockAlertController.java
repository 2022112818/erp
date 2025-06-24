
package com.erp.controller.inventory;

import com.erp.common.result.Result;
import com.erp.dto.inventory.StockAlertDTO;
import com.erp.entity.inventory.StockAlert;
import com.erp.service.inventory.StockAlertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "库存预警")
@RestController
@RequestMapping("/api/inventory/stockalert")
public class StockAlertController {
    @Autowired
    private StockAlertService stockalertService;

    @Operation(summary = "获取所有库存预警")
    @GetMapping("/list")
    public Result<List<StockAlert>> listAll() {
        return Result.success(stockalertService.getAll());
    }

    @Operation(summary = "根据ID获取库存预警")
    @GetMapping("/<built-in function id>")
    public Result<StockAlert> getById(@PathVariable Long id) {
        return Result.success(stockalertService.getById(id));
    }

    @Operation(summary = "创建库存预警")
    @PostMapping("/create")
    public Result<String> create(@RequestBody StockAlertDTO dto) {
        stockalertService.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新库存预警")
    @PutMapping("/update")
    public Result<String> update(@RequestBody StockAlertDTO dto) {
        stockalertService.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除库存预警")
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        stockalertService.delete(id);
        return Result.success("删除成功");
    }
}
