package com.bevelop.devbevelop.domain.study.query;

import com.bevelop.devbevelop.domain.tag.domain.CategoryName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@Getter
public class SearchingTags {

    private final Map<CategoryName, String> tags = new HashMap<>();

    public SearchingTags(final String division, final String job, final String field) {
        tags.put(CategoryName.DIVISION, division);
        tags.put(CategoryName.JOB, job);
        tags.put(CategoryName.FIELD, field);
    }

    public boolean hasBy(CategoryName name) { return !tags.get(name).isEmpty();}

    public String getTagIdsBy(CategoryName name) { return tags.get(name);}

    public static SearchingTags emptyTags() { return new SearchingTags(null, null, null);}
}
