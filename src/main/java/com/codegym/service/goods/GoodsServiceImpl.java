package com.codegym.service.goods;

import com.codegym.model.Goods;
import com.codegym.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Iterable<Goods> findAll() {
        return goodsRepository.findAll();
    }

    @Override
    public Goods findById(Long id) {
        return goodsRepository.findOne(id);
    }

    @Override
    public void save(Goods goods) {
        goodsRepository.save(goods);
    }

    @Override
    public void remove(Long id) {
        goodsRepository.delete(id);
    }
}
