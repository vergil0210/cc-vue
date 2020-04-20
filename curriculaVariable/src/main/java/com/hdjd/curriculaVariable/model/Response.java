package com.hdjd.curriculaVariable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class Response<T> {
    private T data;
    private String msg;
    private List<T> list = new ArrayList<>();
    private String token;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(List<T> list) {
        this.list = list;
    }

    public Response(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}