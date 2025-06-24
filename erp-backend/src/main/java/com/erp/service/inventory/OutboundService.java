
package com.erp.service.inventory;

import com.erp.dto.inventory.OutboundDTO;
import com.erp.entity.inventory.Outbound;
import java.util.List;

public interface OutboundService {
    List<Outbound> getAll();
    Outbound getById(Long id);
    void create(OutboundDTO dto);
    void update(OutboundDTO dto);
    void delete(Long id);
}
