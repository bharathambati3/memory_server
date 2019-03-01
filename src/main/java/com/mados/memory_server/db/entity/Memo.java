package com.mados.memory_server.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "memo")
public class Memo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private String title;

    @Lob
    @Column(name="content", length=512)
    private String content;

    private LocalDateTime createdOn;

    private LocalDateTime learntOn;

    public Memo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getLearntOn() {
        return learntOn;
    }

    public void setLearntOn(LocalDateTime learntOn) {
        this.learntOn = learntOn;
    }
}
