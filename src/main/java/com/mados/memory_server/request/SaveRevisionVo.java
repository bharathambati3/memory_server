package com.mados.memory_server.request;

public class SaveRevisionVo {

    private Long memoryId;
    private String comments;

    public SaveRevisionVo() {
    }

    public Long getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(Long memoryId) {
        this.memoryId = memoryId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
