package com.codegym.repository;

import com.codegym.model.Goods;
import com.codegym.model.User;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Goods, Long> {
    Iterable<Goods> findAllByUser(User user);
}
