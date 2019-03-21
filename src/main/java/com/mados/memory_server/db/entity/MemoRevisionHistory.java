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

    private LocalDateTime revisedOn;

    @Column(name="comments", length = 65535, columnDefinition="TEXT")
    private String content;

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

    public LocalDateTime getRevisedOn() {
        return revisedOn;
    }

    public void setRevisedOn(LocalDateTime revisedOn) {
        this.revisedOn = revisedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
