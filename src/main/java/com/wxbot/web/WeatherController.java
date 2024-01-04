package com.wxbot.web;

import com.alibaba.fastjson.JSONObject;
import com.wxbot.domain.WxbotRepRes;
import com.wxbot.service.WeatherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/weather")
@RestController
public class WeatherController {
    @Resource
    private WeatherService service;
    @RequestMapping("/get")
    public String getWeather(@RequestParam(name = "city", defaultValue = "beijing") String city) {
        WxbotRepRes req = new WxbotRepRes();
        req.setRequestBody(city);
        req.setUrl("/weather/get");
        String response = service.handler(req);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONObject realtime = result.getJSONObject("realtime");
        StringBuffer sb = new StringBuffer();
        sb.append("城市: "+result.getString("city")+"\n");
        sb.append("天气: "+realtime.getString("info")+"\n");
        sb.append("温度: "+realtime.getString("temperature")+"\n");
        sb.append("湿度: "+realtime.getString("humidity")+"\n");
        sb.append("风向: "+realtime.getString("direct")+"\n");
        sb.append("风力: "+realtime.getString("power")+"\n");
        sb.append("空气质量: "+realtime.getString("aqi")+"\n");
        System.out.printf("城市：%s%n", result.getString("city"));
        System.out.printf("天气：%s%n", realtime.getString("info"));
        System.out.printf("温度：%s%n", realtime.getString("temperature"));
        System.out.printf("湿度：%s%n", realtime.getString("humidity"));
        System.out.printf("风向：%s%n", realtime.getString("direct"));
        System.out.printf("风力：%s%n", realtime.getString("power"));
        System.out.printf("空气质量：%s%n", realtime.getString("aqi"));
        return sb.toString();
    }
}

