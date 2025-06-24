
package com.erp.dto.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WarehouseDTO {
    private Long id;
    private String name;
    private String location;
    private String manager;
    private String contact;
}
