package cz.matyas.SAP.Light.v1.service.impl;

import cz.matyas.SAP.Light.v1.dto.GoodsDTO;
import cz.matyas.SAP.Light.v1.entity.GoodsEntity;
import cz.matyas.SAP.Light.v1.mapper.GoodsMapper;
import cz.matyas.SAP.Light.v1.repository.GoodsRepository;
import cz.matyas.SAP.Light.v1.service.GoodsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    GoodsMapper goodsMapper;


    @Override
    public List<GoodsDTO> getAll() {
        List<GoodsEntity> goodsEntityList = goodsRepository.findAll();
        List<GoodsDTO> goodsDTOList = new ArrayList<>();
        goodsEntityList.forEach(goodsEntity -> goodsDTOList.add(goodsMapper.toDTO(goodsEntity)));

        return goodsDTOList;
    }

    @Override
    public GoodsDTO getGoodsById(Long id) {
        GoodsEntity goodsEntity = getGoodsEntityOrThrow(id);

        return goodsMapper.toDTO(goodsEntity);
    }

    @Override
    public GoodsDTO createGoods(GoodsDTO goodsDTO) {
        goodsDTO.setId(null);
        GoodsEntity createdGoodsEntity = goodsRepository.save(goodsMapper.toEntity(goodsDTO));

        return goodsMapper.toDTO(createdGoodsEntity);
    }

    @Override
    public GoodsDTO editGoodsById(Long id, GoodsDTO goodsDTO) {
        getGoodsEntityOrThrow(id);
        GoodsEntity updateGoodsEntity = goodsMapper.toEntity(goodsDTO);
        updateGoodsEntity.setId(id);
        goodsRepository.save(updateGoodsEntity);

        return goodsMapper.toDTO(updateGoodsEntity);
    }

    @Override
    public GoodsDTO deleteGoodsById(Long id) {
        GoodsEntity deletedGoodsEntity = getGoodsEntityOrThrow(id);
        goodsRepository.delete(deletedGoodsEntity);

        return goodsMapper.toDTO(deletedGoodsEntity);
    }

    private GoodsEntity getGoodsEntityOrThrow(Long id){
        Optional<GoodsEntity> goodsEntity = goodsRepository.findById(id);
        if (goodsEntity.isEmpty()){
            throw new EntityNotFoundException("Produkt s id " + id + " nebyl nalezen");
        }

        return goodsEntity.get();
    }
}
