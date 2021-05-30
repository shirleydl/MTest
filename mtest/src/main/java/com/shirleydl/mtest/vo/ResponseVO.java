package com.shirleydl.mtest.vo;

public class ResponseVO {

    private Integer code;

    private Object data;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseVO(Integer code, Object data) {
        super();
        this.code = code;
        this.data = data;
    }

    public ResponseVO(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ResponseVO(Integer code, Object data, String message) {
        super();
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseVO success(T data) {
        return new ResponseVO(ResponseCode.SUCCESS, data);
    }

    public static <T> ResponseVO success(String message) {
        return new ResponseVO(ResponseCode.SUCCESS, message);
    }

    public static <T> ResponseVO success(T data, String message) {
        return new ResponseVO(ResponseCode.SUCCESS, data, message);
    }

    public static ResponseVO failure(String message) {
        return new ResponseVO(ResponseCode.FAILURE, null, message);
    }

    public static <T> ResponseVO failure(T data, String message) {
        return new ResponseVO(ResponseCode.FAILURE, data, message);
    }
}