package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.GpuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gpu} and its DTO {@link GpuDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GpuMapper extends EntityMapper<GpuDTO, Gpu> {
    @Named("gpuName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "gpuName", source = "gpuName")
    GpuDTO toDtoGpuName(Gpu gpu);
}
