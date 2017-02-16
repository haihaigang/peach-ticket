package com.peach.ticket.web.response;

/**
 * Created by hgwang on 2017/2/13.
 */
public class ResponseCode {
    /**
     * 200 正确响应
     */
    public static int OK = 200;

    /**
     * 400 数据格式错误
     */
    public static int LOGIC_ERROR = 400;

    /**
     * 401 未登录
     * */
    public static int UNAUTHORIZED = 401;

    /**
     * 404 数据不存在
     */
    public static int NOT_FOUND = 404;

    /**
     * 500 服务器错误
     */
    public static int SERVICE_ERROR = 500;
}
