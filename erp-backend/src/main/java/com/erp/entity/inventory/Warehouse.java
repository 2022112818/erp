
package com.erp.entity.inventory;

import lombok.Data;

@Data
public class Warehouse {
    private Long id;
    private String name;
    private String location;
    private String manager;
    private String contact;
}
