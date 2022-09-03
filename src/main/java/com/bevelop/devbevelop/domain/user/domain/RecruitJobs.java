package com.bevelop.devbevelop.domain.user.domain;

import lombok.Getter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Embeddable
public class RecruitJobs {

    @ElementCollection
    @CollectionTable(name = "study_recruit_job", joinColumns = @JoinColumn(name = "study_id"))
    private List<RecruitJob> recruitJobList = new ArrayList<>();

    protected RecruitJobs() {

    }

    public RecruitJobs(final List<RecruitJob> recruitJobList) { this.recruitJobList = recruitJobList;}

    public List<RecruitJob> getValue() { return new ArrayList<>(recruitJobList);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecruitJobs that = (RecruitJobs) o;
        return Objects.equals(recruitJobList, that.recruitJobList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recruitJobList);
    }
}
