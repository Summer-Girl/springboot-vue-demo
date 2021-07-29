package com.example.demo.common;

public class Result<T> {
    private String code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public static Result success() {
        Result result = new Result<>();
        result.setCode("1");
        result.setMessage("success");
        return result;
    }

    public static <T> Result success(T data) {
        Result result = new Result<>(data);
        result.setCode("1");
        result.setMessage("success");
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result<>();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }


}
