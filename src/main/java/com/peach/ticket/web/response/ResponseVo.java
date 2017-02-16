package com.peach.ticket.web.response;

/**
 * Created by hgwang on 2017/2/13.
 */
public class ResponseVo {
    private int code;
    private int status;
    private String message;
    private Object body;

    public ResponseVo(){}

    public ResponseVo(Object obj){
        this.setStatus(200);
        this.setBody(obj);
    }

    public ResponseVo(int code, String message){
        this.setStatus(code);
        this.setMessage(message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
