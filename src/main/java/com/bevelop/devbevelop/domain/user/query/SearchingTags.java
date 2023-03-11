package com.bevelop.devbevelop.domain.user.query;

import com.bevelop.devbevelop.domain.tag.domain.UserCategoryName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@Getter
public class SearchingTags {

    private final Map<UserCategoryName, String> tags = new HashMap<>();

    public SearchingTags(final String job, final String interests, final String stack) {
        tags.put(UserCategoryName.JOB, job);
        tags.put(UserCategoryName.INTERESTS, interests);
        tags.put(UserCategoryName.STACK, stack);
    }

    public String getTagIdsBy(UserCategoryName name) { return tags.get(name);}


    public static SearchingTags emptyTags() { return new SearchingTags("","", "");}


}
