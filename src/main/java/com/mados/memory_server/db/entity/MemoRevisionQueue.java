package com.mados.memory_server.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "memo_revision_queue")
public class MemoRevisionQueue {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private MemoRecord memoRecord;

    @Column(name = "next_revision_on")
    private LocalDateTime nextRevisionOn;

    @Column(name = "current_revision_done", columnDefinition = "TINYINT(1)")
    private Boolean isCurrentRevisionDone;

    public MemoRevisionQueue() {
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

    public LocalDateTime getNextRevisionOn() {
        return nextRevisionOn;
    }

    public void setNextRevisionOn(LocalDateTime nextRevisionOn) {
        this.nextRevisionOn = nextRevisionOn;
    }

    public Boolean getCurrentRevisionDone() {
        return isCurrentRevisionDone;
    }

    public void setCurrentRevisionDone(Boolean currentRevisionDone) {
        isCurrentRevisionDone = currentRevisionDone;
    }
}
