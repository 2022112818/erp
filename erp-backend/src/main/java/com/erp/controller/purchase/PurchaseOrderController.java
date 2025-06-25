
package com.erp.controller.purchase;

import com.erp.common.result.Result;
import com.erp.dto.purchase.PurchaseOrderDTO;
import com.erp.entity.purchase.PurchaseOrder;
import com.erp.service.purchase.PurchaseOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "采购订单管理")
@RestController
@RequestMapping("/api/purchase/purchaseOrder")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService service;

    @Operation(summary = "获取所有采购订单管理")
    @GetMapping("/list")
    public Result<List<PurchaseOrder>> listAll() {
        return Result.success(service.getAll());
    }

    @Operation(summary = "根据ID获取采购订单管理")
    @GetMapping("/{id}")
    public Result<PurchaseOrder> getById(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @Operation(summary = "创建采购订单管理")
    @PostMapping("/create")
    public Result<String> create(@RequestBody PurchaseOrderDTO dto) {
        service.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新采购订单管理")
    @PutMapping("/update")
    public Result<String> update(@RequestBody PurchaseOrderDTO dto) {
        service.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除采购订单管理")
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success("删除成功");
    }

    @Operation(summary = "审核采购订单")
    @PutMapping("/approve/{id}")
    public Result<String> approve(@PathVariable Long id) {
        service.approve(id);
        return Result.success("审核通过");
    }

    @Operation(summary = "拒绝采购订单")
    @PutMapping("/reject/{id}")
    public Result<String> reject(@PathVariable Long id, @RequestParam String reason) {
        service.reject(id, reason);
        return Result.success("审核拒绝");
    }

    @Operation(summary = "支付采购订单")
    @PutMapping("/pay/{id}")
    public Result<String> pay(@PathVariable Long id) {
        service.pay(id);
        return Result.success("支付成功");
    }
}
