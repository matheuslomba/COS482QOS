package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.HdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hd} and its DTO {@link HdDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HdMapper extends EntityMapper<HdDTO, Hd> {
    @Named("hdName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "hdName", source = "hdName")
    HdDTO toDtoHdName(Hd hd);
}
