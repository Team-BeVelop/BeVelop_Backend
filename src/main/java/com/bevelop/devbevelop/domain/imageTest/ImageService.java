package com.bevelop.devbevelop.domain.imageTest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    @Transactional
    Long keepImage(MultipartFile imageFile, Image image) throws IOException;
}
