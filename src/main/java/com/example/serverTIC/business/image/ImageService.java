package com.example.serverTIC.business.image;

import com.example.serverTIC.business.activity.ActivityRepository;
import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ActivityRepository activityRepository;

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

    public ResponseEntity getActivityPictures(Long activityId) {
        Optional<Activity> act= activityRepository.findById(activityId);
        if(act.isEmpty()){
            return new ResponseEntity("actividad no existe",HttpStatus.BAD_REQUEST);

        }
        List<Image> dbImage = activityRepository.findImagesByActivity(activityId);
        List<byte[]> byteList = new ArrayList<>();
        for (Image i:dbImage){
            byteList.add(i.getImageData());
        }
        if(dbImage.isEmpty()){
            return new ResponseEntity("actividad no tiene fotos",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(byteList, HttpStatus.OK);
    }

    public void uploadActivityImage(Image image, Long activityId) {
        Optional<Activity> act = activityRepository.findById(activityId);
        if (act.isEmpty()){
            throw new IllegalStateException("actividad no existe");
        }
        Activity activity=act.get();
        activity.addPicture(image);
        activityRepository.save(activity);
    }
}
