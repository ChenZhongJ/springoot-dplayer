package com.player.service;

import com.player.form.DanMuForm;
import com.player.model.VideoDanMu;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DanMuService {

    public static final String DANMAKU_VIDEO_KEY = "DANMAKU_VIDEO_";

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private ModelMapper modelMapper;

    public Boolean save(DanMuForm form){

        VideoDanMu danMu = new VideoDanMu();

        danMu.setVideoId(form.getId());
        danMu.setUserId(form.getAuthor());
        danMu.setDanMuText(form.getText());
        danMu.setDanMuColor(form.getColor());
        danMu.setDanMuTime(form.getTime());
        danMu.setDanMuType(form.getType());
        danMu.setDanMuCreateTime(new Date());
        danMu.setDanMuStatus(0);

        System.out.println(danMu.toString());

        String redisKey = DANMAKU_VIDEO_KEY + danMu.getVideoId();

        System.out.println("save : " + redisKey);

        redisDao.lSet(redisKey, danMu);

        return true;
    }

    public List<Object[]> videoDanMuList(String videoId){

        String redisKey = DANMAKU_VIDEO_KEY + videoId;

        System.out.println("get : " + redisKey);

        if(!redisDao.hasKey(redisKey))
            return null;

        System.out.println("有值");

        List<Object> list = redisDao.lGet(redisKey, 0, -1);

        if(list == null || list.isEmpty())
            return null;

        return parseDanMuListToArray(list);

    }

    private String parseType(int type){
        switch (type){
            case 0: return "right";
            case 1: return "top";
            case 2: return "bottom";
            default: return "right";
        }
    }

    private String parseColor(Long color){
        if(color <= 0)
            return "#fff";
        String str = Long.toHexString(color);
        return "#"+str;
    }

    private List<Object[]> parseDanMuListToArray(List<Object> list){

        List<Object[]> data = new ArrayList<>();

        list.forEach(videoDanMu -> {
            VideoDanMu danMu = (VideoDanMu) videoDanMu;
            Object[] o = new Object[]{danMu.getDanMuTime(), danMu.getDanMuType(),
                    danMu.getDanMuColor(), danMu.getUserId(), danMu.getDanMuText()};
            data.add(o);
        });

        return data;
    }

}
