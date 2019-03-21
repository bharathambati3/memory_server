package com.mados.memory_server.request;

public class SaveRevisionVo {

    private Long id;
    private String comments;

    public SaveRevisionVo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
