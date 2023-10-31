package cz.matyas.SAP.Light.v1.service;

import cz.matyas.SAP.Light.v1.dto.GoodsDTO;

import java.util.List;

public interface GoodsService {
    List<GoodsDTO> getAll();
    GoodsDTO getGoodsById(Long id);
    GoodsDTO createGoods(GoodsDTO goodsDTO);
    GoodsDTO editGoodsById(Long id, GoodsDTO goodsDTO);
    GoodsDTO deleteGoodsById(Long id);
}
