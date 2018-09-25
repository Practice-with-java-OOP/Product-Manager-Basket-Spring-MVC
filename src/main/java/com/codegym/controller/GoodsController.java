package com.codegym.controller;

import com.codegym.model.Goods;
import com.codegym.model.User;
import com.codegym.service.goods.GoodsService;
import com.codegym.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/goods/{id}")
    public String userGoods(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        Iterable<Goods> goods = goodsService.findAllByUser(user);

        double totalPrice = 0;
        for (Goods g : goods) {
            totalPrice += g.getPrice()*g.getQuantity();
        }

        System.out.println(totalPrice);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("goods", goods);
        return "/user/goods";
    }
}
