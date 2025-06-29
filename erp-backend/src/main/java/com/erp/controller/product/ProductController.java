
package com.erp.controller.product;

import com.erp.common.result.Result;
import com.erp.dto.product.ProductDTO;
import com.erp.entity.product.Product;
import com.erp.service.product.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "商品管理")
@RestController
@RequestMapping("/api/product/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @Operation(summary = "获取所有商品管理")
    @GetMapping("/list")
    public Result<List<Product>> listAll() {
        return Result.success(service.getAll());
    }

    @Operation(summary = "根据ID获取商品管理")
    @GetMapping("/<built-in function id>")
    public Result<Product> getById(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @Operation(summary = "创建商品管理")
    @PostMapping("/create")
    @Transactional
    public Result<String> create(@RequestBody ProductDTO dto) {
        service.create(dto);
        return Result.success("创建成功");
    }

    @Operation(summary = "更新商品管理")
    @PutMapping("/update")
    @Transactional
    public Result<String> update(@RequestBody ProductDTO dto) {
        service.update(dto);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除商品管理")
    @DeleteMapping("/delete/{id}")
    @Transactional
    public Result<String> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success("删除成功");
    }
}
