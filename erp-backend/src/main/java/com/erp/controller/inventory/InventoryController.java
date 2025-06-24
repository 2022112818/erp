
package com.erp.controller.inventory;

import com.erp.common.result.Result;
import com.erp.dto.inventory.InventoryDTO;
import com.erp.entity.inventory.Inventory;
import com.erp.service.inventory.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "库存管理")
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Operation(summary = "获取所有库存信息")
    @GetMapping("/list")
    public Result<List<Inventory>> listAllInventory() {
        List<Inventory> inventories = inventoryService.getAllInventory();
        return Result.success(inventories);
    }


    @Operation(summary = "根据ID获取库存信息")
    @GetMapping("/{id}")
    public Result<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        return Result.success(inventory);
    }

    @Operation(summary = "新增库存")
    @PostMapping("/create")
    public Result<String> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        inventoryService.createInventory(inventoryDTO);
        return Result.success("库存新增成功");
    }

    @Operation(summary = "更新库存")
    @PutMapping("/update")
    public Result<String> updateInventory(@RequestBody InventoryDTO inventoryDTO) {
        inventoryService.updateInventory(inventoryDTO);
        return Result.success("库存更新成功");
    }

    @Operation(summary = "删除库存")
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return Result.success("库存删除成功");
    }
}
