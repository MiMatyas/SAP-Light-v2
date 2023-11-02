package cz.matyas.SAP.Light.v1.controller;

import cz.matyas.SAP.Light.v1.dto.GoodsDTO;
import cz.matyas.SAP.Light.v1.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @GetMapping()
    List<GoodsDTO> getAll() {

        return goodsService.getAll();
    }

    @GetMapping("/{goodsId}")
    GoodsDTO getGoodsById(@PathVariable("goodsId") Long id) {

        return goodsService.getGoodsById(id);
    }

    @PostMapping("/create")
    GoodsDTO createGoods(@RequestBody GoodsDTO goodsDTO) {

        return goodsService.createGoods(goodsDTO);
    }

    @PutMapping("/edit/{goodsId}")
    GoodsDTO editGoodsById(@PathVariable("goodsId") Long id, @RequestBody GoodsDTO goodsDTO) {

        return goodsService.editGoodsById(id, goodsDTO);
    }

    @DeleteMapping("/delete/{goodsId}")
    GoodsDTO deleteGoodsById(@PathVariable("goodsId") Long id) {

        return goodsService.deleteGoodsById(id);
    }
}
