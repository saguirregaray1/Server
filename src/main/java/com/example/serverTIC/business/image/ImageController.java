package com.example.serverTIC.business.image;

import com.example.serverTIC.persistence.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public void uploadImage(@RequestBody Image image) {
        imageService.uploadImage(image);
    }

    @PostMapping("/{activityId}")
    public void uploadActivityPicture(@PathVariable Long activityId,@RequestBody Image image) {
        imageService.uploadActivityImage(image,activityId);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<?> getActivityPictures(@PathVariable Long activityId) {
        return imageService.getActivityPictures(activityId);
    }
}


