
package com.erp.service.inventory;

import com.erp.dto.inventory.InboundDTO;
import com.erp.entity.inventory.Inbound;
import java.util.List;

public interface InboundService {
    List<Inbound> getAll();
    Inbound getById(Long id);
    void create(InboundDTO dto);
    void update(InboundDTO dto);
    void delete(Long id);
}
