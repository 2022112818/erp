
package com.erp.entity.purchase;

import lombok.Data;

@Data
public class Supplier {
    private Long id;
    private String name;
    private String address;
    private String contactPerson;
    private String phone;
    private String bankAccount;
}
