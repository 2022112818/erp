
package com.erp.dto.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupplierDTO {
    private Long id;
    private String name;
    private String address;
    private String contactPerson;
    private String phone;
    private String bankAccount;
}
