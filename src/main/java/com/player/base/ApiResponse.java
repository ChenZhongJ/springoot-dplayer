package com.player.base;

import lombok.Data;

/**
 * API格式封装
 */
@Data
public class ApiResponse {

    //状态码
    private int code;

    //状态信息
    private String message;

    //数据
    private Object data;

    //是否更多
    private boolean more;

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {
        this.code = Status.SUCCESS.getCode();
        this.message = Status.SUCCESS.getStandardMessage();
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public static ApiResponse ofMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse ofSuccess(Object data) {
        return new ApiResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getStandardMessage(), data);
    }

    public static ApiResponse ofStatus(Status status) {
        return new ApiResponse(status.getCode(), status.getStandardMessage(), null);
    }

    public enum Status {
        //成功
        SUCCESS(200, "成功"),
        //请求无效
        BAD_REQUEST(400, "请求错误"),

        NOT_FOUND(404, "未找到资源"),

        INTERNAL_SERVER_ERROR(500, "内部服务错误"),

        NOT_LOGIN(10001, "用户未登录"),

        NOT_LIVE(20001, "直播间不存在"),

        NOT_FILE_TOKEN(30001, "获取文件上传token失败"),

        OPTION_NOT_NULL(30002, "填空项不能为空"),

        DANMA_TEXT_NOT_NULL(30003, "弹幕内容不能为空");

        private int code;
        private String standardMessage;

        Status(int code, String standardMessage) {
            this.code = code;
            this.standardMessage = standardMessage;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStandardMessage() {
            return standardMessage;
        }

        public void setStandardMessage(String standardMessage) {
            this.standardMessage = standardMessage;
        }
    }

}
