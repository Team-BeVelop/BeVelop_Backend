package com.bevelop.devbevelop.domain.user.service;

import com.bevelop.devbevelop.domain.auth.dto.UserUpdateDto;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.global.common.response.CommonResult;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    /**
     * 모든 유저 리스트를 반환
     * @return 유저 리스트
     */
    List<User> findAll();

    /**
     * 이메일을 통해 유저 조회
     * @param email
     * @return 조회된 유저
     */
    Optional<User> findByEmail(String email);

    /**
     * 이름을 통해 유저 조회
     * @param name
     * @return 조회된 유저
     */
    Optional<User> findByName(String name);

//  /**
//   * Security Context에 존재하는 인증 정보를 통해 유저 정보 조회
//   * @return 조회된 유저
//   */
//  Optional<User> getMyInfo();

    /**
     * 유저 정보 수정
     * @param user 수정할 User Entity
     * @param userUpdateDto 수정할 정보
     * @return 결과
     */
    CommonResult updateUser(User user, UserUpdateDto userUpdateDto);

    Optional<User> findById(Long id);
    
    CommonResult uploadImage(User user, MultipartFile image) throws IOException;
}