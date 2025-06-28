
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

}
