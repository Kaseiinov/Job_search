package com.example.home_work_49.service.impl;


import com.example.home_work_49.dao.UserImageDao;
import com.example.home_work_49.dto.UserImageDto;
import com.example.home_work_49.exceptions.ImageNotFoundException;
import com.example.home_work_49.models.UserImage;
import com.example.home_work_49.service.ImageService;
import com.example.home_work_49.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final UserImageDao userImageDao;
    private final FileUtil fileUtil;

    @Override
    public String saveImage(UserImageDto userImageDto) {

        String filename = fileUtil.saveUploadFile(userImageDto.getFile(), "images/");
        userImageDao.save(userImageDto.getUserId(), filename);
        return filename;
    }

    @Override
    public ResponseEntity<?> findByName(String imageName) {
        return fileUtil.getOutputFile(imageName, "images/", MediaType.IMAGE_JPEG);
    }

    @Override
    public ResponseEntity<?> findById(long imageId) {
        UserImage image = userImageDao.getImageById(imageId)
                .orElseThrow(ImageNotFoundException::new);
        String filename = image.getFileName();
        log.info("Found image with name: {}", filename);
        return fileUtil.getOutputFile(filename, "images/", MediaType.IMAGE_JPEG);
    }

    @Override
    public ResponseEntity<?> findByUserId(long movieId) {
        UserImage image = userImageDao.findByUserId(movieId)
                .orElseThrow(ImageNotFoundException::new);
        String filename = image.getFileName();
        return fileUtil.getOutputFile(filename, "images/", MediaType.IMAGE_JPEG);
    }

}
