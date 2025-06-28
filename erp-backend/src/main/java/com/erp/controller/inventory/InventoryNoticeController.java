package com.erp.controller.inventory;

import com.erp.common.result.Result;
import com.erp.dto.inventory.InventoryNoticeDTO;
import com.erp.service.inventory.InventoryNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "仓库通知")
@RestController
@RequestMapping("/api/inventory/notice")
public class InventoryNoticeController {
    @Autowired
    private InventoryNoticeService service;

    @Operation(summary = "获取所有未过期的出入库通知")
    @GetMapping("/list")
    public Result<List<InventoryNoticeDTO>> list() {
        List<InventoryNoticeDTO> list = service.list();
        return Result.success(list);
    }
}
