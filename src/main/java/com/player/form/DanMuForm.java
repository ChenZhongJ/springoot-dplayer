package com.player.form;

import lombok.Data;

@Data
public class DanMuForm {

    private String id;

    private Long author;

    private Long color;

    private String text;

    private double time;

    private int type;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"author\":")
                .append(author);
        sb.append(",\"color\":")
                .append(color);
        sb.append(",\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"text\":\"")
                .append(text).append('\"');
        sb.append(",\"time\":")
                .append(time);
        sb.append(",\"type\":")
                .append(type);
        sb.append('}');
        return sb.toString();
    }
}

