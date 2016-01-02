package com.hmjf.domain

/**
 * Created by jack on 16/1/2.
 */
class Result<T> {

    public int code;
    public String message;
    public T body;
    public int requestId;


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", body=" + body +
                ", requestId=" + requestId +
                '}';
    }
}
