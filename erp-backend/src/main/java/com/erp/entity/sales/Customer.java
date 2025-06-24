
package com.erp.entity.sales;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String name;
    private String address;
    private String contactPerson;
    private String phone;
}
