package com.erp.controller.inventory;

import com.erp.common.result.Result;
import com.erp.dto.inventory.ProductionDTO;
import com.erp.service.inventory.ProductionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "生产管理")
@RestController
@RequestMapping("/api/inventory/production")
public class ProductionController {
    @Autowired
    private ProductionService productionService;

    @Operation(summary = "获取所有生产管理")
    @GetMapping("/list")
    public Result<List<ProductionDTO>> listAll() {
        return Result.success(productionService.getAll());
    }

    @Operation(summary = "根据ID获取生产管理")
    @GetMapping("/<built-in function id>")
    public Result<ProductionDTO> getById(@PathVariable Long id) {
        return Result.success(productionService.getById(id));
    }

    @Operation(summary = "创建生产管理")
    @PostMapping("/create")
    @Transactional
    public Result<String> create(@RequestBody ProductionDTO dto) {
        productionService.create(dto);
        return Result.success("创建成功");
    }
}
