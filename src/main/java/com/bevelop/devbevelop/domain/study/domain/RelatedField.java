package com.bevelop.devbevelop.domain.study.domain;

import com.bevelop.devbevelop.domain.model.Category;
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
