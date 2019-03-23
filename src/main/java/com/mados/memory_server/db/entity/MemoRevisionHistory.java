package com.mados.memory_server.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "memo_revision_history")
public class MemoRevisionHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memo_id")
    private MemoRecord memoRecord;

    @Column(name = "to_be_revised_on")
    private LocalDateTime toBeRevisedOn;

    private LocalDateTime revisedOn;

    @Column(name="comments", length = 65535, columnDefinition="TEXT")
    private String comments;

    public MemoRevisionHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MemoRecord getMemoRecord() {
        return memoRecord;
    }

    public void setMemoRecord(MemoRecord memoRecord) {
        this.memoRecord = memoRecord;
    }

    public LocalDateTime getToBeRevisedOn() {
        return toBeRevisedOn;
    }

    public void setToBeRevisedOn(LocalDateTime toBeRevisedOn) {
        this.toBeRevisedOn = toBeRevisedOn;
    }

    public LocalDateTime getRevisedOn() {
        return revisedOn;
    }

    public void setRevisedOn(LocalDateTime revisedOn) {
        this.revisedOn = revisedOn;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
