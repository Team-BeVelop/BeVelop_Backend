package com.bevelop.devbevelop.domain.study.domain;

import com.bevelop.devbevelop.domain.model.Division;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Division division;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String shortTitle;

    @Column
    private String url;

    @Column(nullable = false)
    private String description;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    public Content(final Division division, final String title, final String shortTitle, final String url, String description, LocalDate startDate, LocalDate endDate) {
        this.division = division;
        this.title = title;
        this.shortTitle = shortTitle;
        this.url = url;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Division getDivision() { return division;}

    public String getTitle() { return title;}

    public String getShortTitle() { return shortTitle;}

    public String getUrl() { return url;}

    public String getDescription() { return description;}

    public LocalDate getStartDate() { return startDate;}

    public LocalDate getEndDate() {return endDate;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return division == content.division && Objects.equals(title, content.title) && Objects.equals(shortTitle, content.shortTitle) && Objects.equals(url, content.url) && Objects.equals(description, content.description) && Objects.equals(startDate, content.startDate) && Objects.equals(endDate, content.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(division, title, shortTitle, url, description, startDate, endDate);
    }
}
