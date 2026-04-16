package com.motorliberacaocredito.infrastructure.mapper;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.infrastructure.persistence.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,  uses = {TransacaoMapper.class} )

public interface ClienteMapper {

    ClienteModel toModel(ClienteEntity entity);
    ClienteEntity toEntity(ClienteModel model);
}
