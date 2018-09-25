package com.codegym.service.goods;

import com.codegym.model.Goods;
import com.codegym.model.User;

public interface GoodsService {

    Iterable<Goods> findAll();

    Goods findById(Long id);

    void save(Goods goods);

    void remove(Long id);

    Iterable<Goods> findAllByUser(User user);
}
