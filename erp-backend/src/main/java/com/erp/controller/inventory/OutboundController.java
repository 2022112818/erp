
package com.erp.controller.inventory;

import com.erp.common.result.Result;
import com.erp.dto.inventory.OutboundDTO;
import com.erp.entity.inventory.Outbound;
import com.erp.service.inventory.OutboundService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "出库管理")
@RestController
@RequestMapping("/api/inventory/outbound")
public class OutboundController {
    @Autowired
    private OutboundService outboundService;

    @Operation(summary = "获取所有出库管理")
    @GetMapping("/list")
    public Result<List<Outbound>> listAll() {
        return Result.success(outboundService.getAll());
    }

    @Operation(summary = "根据ID获取出库管理")
    @GetMapping("/<built-in function id>")
    public Result<Outbound> getById(@PathVariable Long id) {
        return Result.success(outboundService.getById(id));
    }

    @Operation(summary = "创建出库管理")
    @PostMapping("/create")
    @Transactional
    public Result<String> create(@RequestBody OutboundDTO dto) {
        outboundService.create(dto);
        return Result.success("创建成功");
    }
}
