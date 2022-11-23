package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CpuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cpu} and its DTO {@link CpuDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CpuMapper extends EntityMapper<CpuDTO, Cpu> {
    @Named("cpuName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpuName", source = "cpuName")
    CpuDTO toDtoCpuName(Cpu cpu);
}
