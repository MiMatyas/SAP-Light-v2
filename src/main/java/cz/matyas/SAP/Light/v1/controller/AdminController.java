package cz.matyas.SAP.Light.v1.controller;

import cz.matyas.SAP.Light.v1.dto.GoodsDTO;
import cz.matyas.SAP.Light.v1.dto.OrderDTO;
import cz.matyas.SAP.Light.v1.service.GoodsService;
import cz.matyas.SAP.Light.v1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Secured(value = {"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class AdminController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @GetMapping("/goods")
    List<GoodsDTO> findAllGoods(){

        return goodsService.getAll();
    }
    @GetMapping("/goods/{goodsId}")
    GoodsDTO getGoodsById(@PathVariable("goodsId") Long id) {

        return goodsService.getGoodsById(id);
    }
    @PostMapping("/goods/create")
    GoodsDTO createGoods(@RequestBody GoodsDTO goodsDTO) {

        return goodsService.createGoods(goodsDTO);
    }
    @PutMapping("/goods/edit/{goodsId}")
    GoodsDTO editGoodsById(@PathVariable("goodsId") Long id, @RequestBody GoodsDTO goodsDTO) {

        return goodsService.editGoodsById(id, goodsDTO);
    }
    @DeleteMapping("/goods/delete/{goodsId}")
    GoodsDTO deleteGoodsById(@PathVariable("goodsId") Long id) {

        return goodsService.deleteGoodsById(id);
    }
    @PostMapping("/order/create")
    OrderDTO createOrderForReceiving(@RequestBody OrderDTO orderDTO){

        return orderService.createOrderForReceiving(orderDTO);
    }
}
