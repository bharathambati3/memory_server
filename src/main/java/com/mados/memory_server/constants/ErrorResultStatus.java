package com.mados.memory_server.constants;

import java.text.MessageFormat;

public enum ErrorResultStatus {
    // 1 - 99 Invalid requests.
    INVALID_CATEGORY_ID(1, "Invalid request: Given category id {0} is not valid"),
    INVALID_TOPIC_ID(2, "Invalid request: Given topic id {0} is not valid"),
    INVALID_MEMO_ID(3, "Invalid request: Given memory id {0} is not valid"),
    INVALID_MEMO_UPDATE_REQ(4, "Invalid request: Given title & content are null");

    private int status;
    private String message;

    ErrorResultStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
