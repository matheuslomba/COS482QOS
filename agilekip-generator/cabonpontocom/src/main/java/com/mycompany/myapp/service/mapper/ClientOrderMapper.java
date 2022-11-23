package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientOrder} and its DTO {@link ClientOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientOrderMapper extends EntityMapper<ClientOrderDTO, ClientOrder> {}
