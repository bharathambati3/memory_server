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

    @Column(name = "to_be_revised_on")
    private LocalDateTime toBeRevisedOn;

    @Column(name = "current_revision_date")
    private LocalDateTime currentRevisionDate;

    @Column(name = "next_revision_on")
    private LocalDateTime nextRevisionOn;

    @ManyToOne
    @JoinColumn(name = "current_revision_id")
    private RevisionPattern currentRevisionPattern;

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

    public LocalDateTime getToBeRevisedOn() {
        return toBeRevisedOn;
    }

    public void setToBeRevisedOn(LocalDateTime toBeRevisedOn) {
        this.toBeRevisedOn = toBeRevisedOn;
    }

    public LocalDateTime getCurrentRevisionDate() {
        return currentRevisionDate;
    }

    public void setCurrentRevisionDate(LocalDateTime currentRevisionDate) {
        this.currentRevisionDate = currentRevisionDate;
    }

    public LocalDateTime getNextRevisionOn() {
        return nextRevisionOn;
    }

    public void setNextRevisionOn(LocalDateTime nextRevisionOn) {
        this.nextRevisionOn = nextRevisionOn;
    }

    public RevisionPattern getCurrentRevisionPattern() {
        return currentRevisionPattern;
    }

    public void setCurrentRevisionPattern(RevisionPattern currentRevisionPattern) {
        this.currentRevisionPattern = currentRevisionPattern;
    }
}
