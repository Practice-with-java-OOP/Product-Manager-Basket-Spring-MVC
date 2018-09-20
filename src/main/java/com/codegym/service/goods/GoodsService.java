package com.codegym.service.goods;

import com.codegym.model.Goods;

public interface GoodsService {

    Iterable<Goods> findAll();

    Goods findById(Long id);

    void save(Goods goods);

    void remove(Long id);
}
