package cz.matyas.SAP.Light.v1.mapper;

import cz.matyas.SAP.Light.v1.dto.GoodsDTO;
import cz.matyas.SAP.Light.v1.entity.GoodsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoodsMapper {
    GoodsDTO toDTO(GoodsEntity goodsEntity);
    GoodsEntity toEntity(GoodsDTO goodsDTO);
}
