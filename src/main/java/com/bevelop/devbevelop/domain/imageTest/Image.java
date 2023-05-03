package com.bevelop.devbevelop.domain.imageTest;

import com.bevelop.devbevelop.domain.auth.dto.UserUpdateDto;
import com.bevelop.devbevelop.global.entity.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "image_test")
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column
    private String imageUrl;

    public Image(Long imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }
}
