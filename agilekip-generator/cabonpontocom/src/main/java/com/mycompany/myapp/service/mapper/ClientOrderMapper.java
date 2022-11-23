package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientOrder} and its DTO {@link ClientOrderDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { MotherBoardMapper.class, CpuMapper.class, GpuMapper.class, RamMapper.class, HdMapper.class, PowerSourceMapper.class }
)
public interface ClientOrderMapper extends EntityMapper<ClientOrderDTO, ClientOrder> {
    @Mapping(target = "motherBoard", source = "motherBoard", qualifiedByName = "motherBoardName")
    @Mapping(target = "cpu", source = "cpu", qualifiedByName = "cpuName")
    @Mapping(target = "gpu", source = "gpu", qualifiedByName = "gpuName")
    @Mapping(target = "ram", source = "ram", qualifiedByName = "ramName")
    @Mapping(target = "hd", source = "hd", qualifiedByName = "hdName")
    @Mapping(target = "powerSource", source = "powerSource", qualifiedByName = "powerSourceName")
    ClientOrderDTO toDto(ClientOrder s);
}
