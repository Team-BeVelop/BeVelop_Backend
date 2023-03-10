package com.bevelop.devbevelop.domain.user.service;

import com.bevelop.devbevelop.domain.auth.dto.UserUpdateDto;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.common.response.ResponseService;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.bevelop.devbevelop.global.util.S3Uploader;
import com.bevelop.devbevelop.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final S3Uploader s3Uploader;
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByNickname(name);
    }

    @Override
    @Transactional
    public CommonResult updateUser(User user, UserUpdateDto userUpdateDto) {
        user.update(userUpdateDto);
        return responseService.getSuccessResult();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

	@Override
	public CommonResult uploadImage(User user, MultipartFile image) throws IOException {
        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image, "images");
            user.setImageUrl(storedFileName);
        }
		return responseService.getSuccessResult();
	}
}