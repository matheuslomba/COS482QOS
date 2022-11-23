package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PowerSourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PowerSource} and its DTO {@link PowerSourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PowerSourceMapper extends EntityMapper<PowerSourceDTO, PowerSource> {
    @Named("powerSourceName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "powerSourceName", source = "powerSourceName")
    PowerSourceDTO toDtoPowerSourceName(PowerSource powerSource);
}
