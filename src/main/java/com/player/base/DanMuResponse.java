package com.player.base;

import lombok.Data;

import java.util.List;

@Data
public class DanMuResponse {

    //状态码
    private int code;

    //数据
    private List<Object[]> data;

    public DanMuResponse(int code, List<Object[]> data) {
        this.code = code;
        this.data = data;
    }
}
