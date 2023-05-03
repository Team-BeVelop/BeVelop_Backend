package com.bevelop.devbevelop.domain.imageTest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = {"Image Controller"})
@RestController
@RequestMapping("/img")
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private final ImageService imageService;

    @ResponseBody   // Long 타입을 리턴하고 싶은 경우 붙여야 함 (Long - 객체)
    @PostMapping(value="/image/new",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long saveImage(HttpServletRequest request, @RequestParam(value="image") MultipartFile imageFile, Image image) throws IOException {
        System.out.println("ImageController.saveImage");
        System.out.println(imageFile);
        System.out.println(image);
        System.out.println("------------------------------------------------------");
        Long diaryId = imageService.keepImage(imageFile, image);
        return diaryId;
    }

}