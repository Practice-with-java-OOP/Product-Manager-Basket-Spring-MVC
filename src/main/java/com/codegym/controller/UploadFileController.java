package com.codegym.controller;

import com.codegym.model.Image;
import com.codegym.model.Product;
import com.codegym.service.image.ImageService;
import com.codegym.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@Transactional
public class UploadFileController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private Environment environment;

    @PostMapping("/products/upload")
    public String fileUpload(@RequestParam("file")CommonsMultipartFile[] files, @RequestParam("id") Long id) {
        final String UPLOADED_FOLDER = environment.getProperty("url.Image");
        final String uploadRootDir = environment.getProperty("url.RootDir");

        Product product = productService.findById(id);
        List<Image> images = product.getImages();

        for (CommonsMultipartFile multipartFile : files) {
            if (multipartFile.isEmpty()) {
                continue;
            }

            Image image = new Image();
            String name = UUID.randomUUID().toString() + ".jpg";

            image.setName(name);
            image.setProduct(product);

            imageService.save(image);
            images.add(image);

            try {
                File serverFile = new File(uploadRootDir + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(multipartFile.getBytes());
                stream.close();

                byte[] bytes = multipartFile.getBytes();
                Path filePath = Paths.get(UPLOADED_FOLDER + File.separator + image.getName());
                Files.write(filePath, bytes);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error Write file:" + image.getName());
            }
        }

        product.setImages(images);
        return "redirect:/admin";
    }
}
