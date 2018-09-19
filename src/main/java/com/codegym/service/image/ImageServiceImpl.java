package com.codegym.service.image;

import com.codegym.model.Image;
import com.codegym.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findOne(id);
    }

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void remove(Long id) {
        imageRepository.delete(id);
    }
}
