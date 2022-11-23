package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ram} and its DTO {@link RamDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RamMapper extends EntityMapper<RamDTO, Ram> {
    @Named("ramName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "ramName", source = "ramName")
    RamDTO toDtoRamName(Ram ram);
}
