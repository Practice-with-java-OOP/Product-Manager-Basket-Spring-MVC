package com.codegym.service.image;

import com.codegym.model.Image;

public interface ImageService {
    Iterable<Image> findAll();

    Image findById(Long id);

    void save(Image image);

    void remove(Long id);
}
