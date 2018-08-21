package com.player.controller;

import com.player.base.ApiResponse;
import com.player.base.DanMuResponse;
import com.player.form.DanMuForm;
import com.player.model.VideoDanMu;
import com.player.service.DanMuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class DanMuController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DanMuService danMuService;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/video")
    @ResponseBody
    public ApiResponse videoStr(@RequestParam("videoId") String videoId){

        List<String> list = new ArrayList<>();

        String sd = "标清";
        String sd_url = "http://pbfw7pqeq.bkt.clouddn.com/sd.mp4";

        list.add(sd_url);

        String hd = "高清";
        String hd_url = "http://pbfw7pqeq.bkt.clouddn.com/hd.mp4";

        list.add(hd_url);

        String fhd = "超清";
        String fhd_url = "http://pbfw7pqeq.bkt.clouddn.com/fhd.mp4";

        list.add(fhd_url);

        return ApiResponse.ofSuccess(list);
    }

    @GetMapping(value = "/dplayer/v3")
    @ResponseBody
    public DanMuResponse getDanMuList(@RequestParam String id){

        System.out.println(id);

        List<Object[]> danmaku = danMuService.videoDanMuList(id);

        if(danmaku == null)
            return new DanMuResponse(0, null);

        danmaku.forEach(objects -> objects.toString());


        return new DanMuResponse(0, danmaku);
    }

    @PostMapping(value = "/dplayer/v3")
    @ResponseBody
    public DanMuResponse sendDanMu(@RequestBody DanMuForm danMuForm) {

        System.out.println(danMuForm.toString());

        if (danMuForm == null || danMuForm.getText().trim().isEmpty())
            return new DanMuResponse(0, null);

        danMuService.save(danMuForm);

        Object[] o = new Object[]{danMuForm.getTime(), danMuForm.getType(), danMuForm.getColor(), danMuForm.getAuthor(), danMuForm.getText()};

        return new DanMuResponse(0, Collections.singletonList(o));
    }
}
