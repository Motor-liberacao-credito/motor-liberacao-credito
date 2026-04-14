package com.motorliberacaocredito.infrastructure.mapper;


import com.motorliberacaocredito.domain.model.TransacaoModel;
import com.motorliberacaocredito.infrastructure.persistence.entity.TransacaoEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TransacaoMapper {

    TransacaoModel toModel(TransacaoEntity entity);
    TransacaoEntity toEntity(TransacaoModel model);

}
