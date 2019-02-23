package com.mados.memory_server.constants;

import java.text.MessageFormat;

public enum ErrorResultStatus {
    // 1 - 99 Invalid requests.
    INVALID_CATEGORY_ID(1, "Given category id {0} is not valid"),
    INVALID_TOPIC_ID(2, "Given topic id {0} is not valid");

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
