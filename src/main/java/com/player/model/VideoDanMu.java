package com.player.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VideoDanMu implements Serializable {

    // 弹幕ID
    private Long danMuId;

    // 弹幕池ID
    private String videoId;

    // 发送弹幕的用户
    private Long userId;

    // 发送内容
    private String danMuText;

    // 颜色
    private Long danMuColor;

    // 弹幕发送时间
    private double danMuTime;

    // 弹幕类型(滚动, 顶部, 底部)
    private int danMuType;

    // 弹幕创建时间
    private Date danMuCreateTime;

    // 弹幕状态
    private int danMuStatus;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"danMuId\":")
                .append(danMuId);
        sb.append(",\"videoId\":\"")
                .append(videoId).append('\"');
        sb.append(",\"userId\":")
                .append(userId);
        sb.append(",\"danMuText\":\"")
                .append(danMuText).append('\"');
        sb.append(",\"danMuColor\":")
                .append(danMuColor);
        sb.append(",\"danMuTime\":")
                .append(danMuTime);
        sb.append(",\"danMuType\":\"")
                .append(danMuType).append('\"');
        sb.append(",\"danMuCreateTime\":\"")
                .append(danMuCreateTime).append('\"');
        sb.append(",\"danMuStatus\":")
                .append(danMuStatus);
        sb.append('}');
        return sb.toString();
    }
}
