package com.mados.memory_server.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "revision_pattern")
public class RevisionPattern {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Long duration;

    @ManyToOne
    @JoinColumn(name = "revision_pattern_type_id")
    private RevisionPatternType type;

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

    public RevisionPatternType getType() {
        return type;
    }

    public void setType(RevisionPatternType type) {
        this.type = type;
    }
}
