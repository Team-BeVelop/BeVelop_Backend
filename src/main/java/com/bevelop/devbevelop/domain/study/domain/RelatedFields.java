package com.bevelop.devbevelop.domain.study.domain;

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
public class RelatedFields {

    @ElementCollection
    @CollectionTable(name = "study_related_field", joinColumns = @JoinColumn(name = "study_id"))
    private List<RelatedField> relatedFields = new ArrayList<>();

    protected RelatedFields() {

    }

    public RelatedFields(final List<RelatedField> relatedFields) { this.relatedFields = relatedFields;}

    public List<RelatedField> getValue() { return new ArrayList<>(relatedFields);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelatedFields that = (RelatedFields) o;
        return Objects.equals(relatedFields, that.relatedFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relatedFields);
    }
}
