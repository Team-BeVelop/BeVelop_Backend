package com.bevelop.devbevelop.domain.user.domain;

import lombok.Getter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Embeddable
public class AttachedStacks {

    @ElementCollection
    @CollectionTable(name = "stack_tag", joinColumns = @JoinColumn(name = "user_id"))
    private Set<AttachedStack> attachedStacks = new HashSet<>();

    protected  AttachedStacks() {
    }

    public AttachedStacks(final Set<AttachedStack> attachedStacks) {
        this.attachedStacks = attachedStacks;
    }

    public Set<AttachedStack> getValue() { return new HashSet<>(attachedStacks); }

    public static AttachedStacks empty() { return new AttachedStacks(Set.of());}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachedStacks that = (AttachedStacks) o;
        return Objects.equals(attachedStacks, that.attachedStacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachedStacks);
    }
}
