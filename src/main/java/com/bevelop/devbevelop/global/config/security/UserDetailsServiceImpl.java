package com.bevelop.devbevelop.global.config.security;

import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;


    public User save(User user){
        validateDuplicateMember(user);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateDuplicateMember(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if(findUser.isPresent()) throw new CustomException(ErrorCode.MEMBER_EXISTS);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws CustomException {

        if(email.contains("@")) {

            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().toString())
                    .build();
        }
        String provider = (email.contains("k"))? "kakao" : "github";
        User user = userRepository.findBySocialIdAndProvider(email, provider).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getSocialId())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
}
