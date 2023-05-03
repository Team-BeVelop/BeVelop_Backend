package com.bevelop.devbevelop.domain.imageTest;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bevelop.devbevelop.global.config.S3Config;
import com.bevelop.devbevelop.global.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService{
//
    // 의존성 주입
    private final ImageRepository imageRepository;

    @Autowired
    private S3Uploader s3Uploader;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public Long keepImage(MultipartFile imageFile, Image image) throws IOException {
        System.out.println("Image service saveImage");
        if (!imageFile.isEmpty()) {
            String storedFileName = s3Uploader.upload(imageFile, "images");
            image.setImageUrl(storedFileName);
        }
        Image savedImage = imageRepository.save(image);
        return savedImage.getImageId();
    }
}