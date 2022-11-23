package com.mycompany.myapp.process.clientOrderProcess;

import com.mycompany.myapp.domain.ClientOrder;
import com.mycompany.myapp.domain.ClientOrderProcess;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
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
    @Mapping(target = "orderComponents", source = "orderComponents")
    ClientOrderDTO toClientOrderDTO(ClientOrder clientOrder);
}
