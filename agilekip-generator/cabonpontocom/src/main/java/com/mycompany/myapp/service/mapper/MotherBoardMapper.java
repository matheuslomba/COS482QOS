package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MotherBoardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MotherBoard} and its DTO {@link MotherBoardDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MotherBoardMapper extends EntityMapper<MotherBoardDTO, MotherBoard> {
    @Named("motherBoardName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "motherBoardName", source = "motherBoardName")
    MotherBoardDTO toDtoMotherBoardName(MotherBoard motherBoard);
}
