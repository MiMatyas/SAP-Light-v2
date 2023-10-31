package cz.matyas.SAP.Light.v1.mapper;

import cz.matyas.SAP.Light.v1.dto.OrderDTO;
import cz.matyas.SAP.Light.v1.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "addressId", source = "address.id")
    @Mapping(target = "goodsIds", expression = "java(getGoodsIds(orderEntity))")
    OrderDTO toDTO(OrderEntity orderEntity);
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "address.id", source = "addressId")
    @Mapping(target = "goods", ignore = true)
    OrderEntity toEntity(OrderDTO orderDTO);

    default List<Long> getGoodsIds(OrderEntity orderEntity){
        List<Long> goodsIds = new ArrayList<>();
        orderEntity.getGoods().forEach(goodsEntity -> goodsIds.add(goodsEntity.getId()));

        return goodsIds;
    }
}
