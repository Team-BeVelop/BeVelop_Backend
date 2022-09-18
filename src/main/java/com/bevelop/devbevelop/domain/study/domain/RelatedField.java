package com.bevelop.devbevelop.domain.study.domain;

import lombok.*;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class RelatedField {

    @Column(name ="field_name", nullable = false)
    private String fieldName;
}
