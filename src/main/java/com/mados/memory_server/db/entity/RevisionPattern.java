package com.mados.memory_server.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "revision_pattern")
public class RevisionPattern {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Long duration;

    @ManyToOne
    @JoinColumn(name = "revision_pattern_type_id")
    private RevisionPatternType revisionPatternType;

    public RevisionPattern() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public RevisionPatternType getRevisionPatternType() {
        return revisionPatternType;
    }

    public void setRevisionPatternType(RevisionPatternType revisionPatternType) {
        this.revisionPatternType = revisionPatternType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevisionPattern that = (RevisionPattern) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
