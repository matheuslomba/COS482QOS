package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientOrderProcess} and its DTO {@link ClientOrderProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, ClientOrderMapper.class })
public interface ClientOrderProcessMapper extends EntityMapper<ClientOrderProcessDTO, ClientOrderProcess> {
    @Mapping(target = "processInstance", source = "processInstance")
    @Mapping(target = "clientOrder", source = "clientOrder")
    ClientOrderProcessDTO toDto(ClientOrderProcess s);
}
