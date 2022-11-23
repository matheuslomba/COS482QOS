package com.mycompany.myapp.process.clientOrderProcess;

import com.mycompany.myapp.domain.ClientOrder;
import com.mycompany.myapp.domain.ClientOrderProcess;
import com.mycompany.myapp.domain.Cpu;
import com.mycompany.myapp.domain.Gpu;
import com.mycompany.myapp.domain.Hd;
import com.mycompany.myapp.domain.MotherBoard;
import com.mycompany.myapp.domain.PowerSource;
import com.mycompany.myapp.domain.Ram;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
import com.mycompany.myapp.service.dto.CpuDTO;
import com.mycompany.myapp.service.dto.GpuDTO;
import com.mycompany.myapp.service.dto.HdDTO;
import com.mycompany.myapp.service.dto.MotherBoardDTO;
import com.mycompany.myapp.service.dto.PowerSourceDTO;
import com.mycompany.myapp.service.dto.RamDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskChooseComponentsMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    ClientOrderProcessDTO toClientOrderProcessDTO(ClientOrderProcess clientOrderProcess);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "orderID", source = "orderID")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "clientName", source = "clientName")
    @Mapping(target = "numComponents", source = "numComponents")
    @Mapping(target = "motherBoard", source = "motherBoard")
    @Mapping(target = "cpu", source = "cpu")
    @Mapping(target = "gpu", source = "gpu")
    @Mapping(target = "ram", source = "ram")
    @Mapping(target = "hd", source = "hd")
    @Mapping(target = "powerSource", source = "powerSource")
    ClientOrderDTO toClientOrderDTO(ClientOrder clientOrder);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "motherBoardName", source = "motherBoardName")
    MotherBoardDTO toMotherBoardDTO(MotherBoard motherBoardName);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpuName", source = "cpuName")
    CpuDTO toCpuDTO(Cpu cpuName);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "gpuName", source = "gpuName")
    GpuDTO toGpuDTO(Gpu gpuName);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "ramName", source = "ramName")
    RamDTO toRamDTO(Ram ramName);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "hdName", source = "hdName")
    HdDTO toHdDTO(Hd hdName);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "powerSourceName", source = "powerSourceName")
    PowerSourceDTO toPowerSourceDTO(PowerSource powerSourceName);
}
