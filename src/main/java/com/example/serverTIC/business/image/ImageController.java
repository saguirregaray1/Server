package com.example.serverTIC.business.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public void uploadImage(@RequestBody Image image) throws IOException {
        imageService.uploadImage(image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        return imageService.getImage(id);
    }
}


