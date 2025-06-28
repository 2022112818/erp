package com.erp.dto.inventory;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryNoticeDTO {
    private String productName;
    private Integer quantity;
    private LocalDate date;
    private String noticeType;
}
