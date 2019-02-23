package com.mados.memory_server.request;

import com.mados.memory_server.constants.ErrorResultStatus;

import java.text.MessageFormat;

public class BaseResponse<T> {
    int status;
    String message;
    T data;

    public BaseResponse(T data) {
        this.status = 0;
        this.message = "Success";
        this.data = data;
    }

    public BaseResponse(ErrorResultStatus code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
    }

    public BaseResponse(ErrorResultStatus code, Object... args) {
        this.status = code.getStatus();
        this.message = MessageFormat.format(code.getMessage(), args);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
