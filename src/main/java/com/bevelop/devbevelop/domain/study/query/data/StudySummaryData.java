package com.bevelop.devbevelop.domain.study.query.data;

import com.bevelop.devbevelop.domain.project.domain.Division;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudySummaryData {

    private Long id;
    private String division;
    private String title;
    private String shortTitle;

}