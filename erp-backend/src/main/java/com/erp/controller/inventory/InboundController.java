
package com.erp.controller.inventory;

import com.erp.common.result.Result;
import com.erp.dto.inventory.InboundDTO;
import com.erp.entity.inventory.Inbound;
import com.erp.service.inventory.InboundService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "入库管理")
@RestController
@RequestMapping("/api/inventory/inbound")
public class InboundController {
    @Autowired
    private InboundService inboundService;

    @Operation(summary = "获取所有入库管理")
    @GetMapping("/list")
    public Result<List<Inbound>> listAll() {
        return Result.success(inboundService.getAll());
    }

    @Operation(summary = "根据ID获取入库管理")
    @GetMapping("/<built-in function id>")
    public Result<Inbound> getById(@PathVariable Long id) {
        return Result.success(inboundService.getById(id));
    }

    @Operation(summary = "创建入库管理")
    @PostMapping("/create")
    public Result<String> create(@RequestBody InboundDTO dto) {
        inboundService.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新入库管理")
    @PutMapping("/update")
    public Result<String> update(@RequestBody InboundDTO dto) {
        inboundService.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除入库管理")
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        inboundService.delete(id);
        return Result.success("删除成功");
    }
}
