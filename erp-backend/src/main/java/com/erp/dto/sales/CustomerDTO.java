
package com.erp.dto.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private String address;
    private String contactPerson;
    private String phone;
}
