package com.example.serverTIC.business.image;

import com.example.serverTIC.persistence.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void uploadImage(Image image) {
        imageRepository.save(image);
    }

    @Transactional
    public ResponseEntity getImage(Long id) {
        Optional<Image> dbImage = imageRepository.findById(id);
        if(dbImage.isEmpty()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(dbImage.get().getImageData(), HttpStatus.OK);
    }
}
