package com.mados.memory_server.request;

import java.time.LocalDateTime;

public class CreateMemoVo {

    private Integer topicId;
    private String title;
    private String content;
    private LocalDateTime learntOn;

    public CreateMemoVo() {
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
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

    public LocalDateTime getLearntOn() {
        return learntOn;
    }

    public void setLearntOn(LocalDateTime learntOn) {
        this.learntOn = learntOn;
    }
}
