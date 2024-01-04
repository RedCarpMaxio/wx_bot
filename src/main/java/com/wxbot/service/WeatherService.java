package com.wxbot.service;

import com.wxbot.domain.WxbotRepRes;
import com.wxbot.untils.Http;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
@Service
public class WeatherService extends CommonAbstractService{
    @Override
    public WxbotRepRes queryOut(WxbotRepRes req) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", "fb0d61623ae47f314200cdfa2332a02f");
        params.put("city", req.getRequestBody());
        String paramsStr = urlencode(params);
        String url = "http://apis.juhe.cn/simpleWeather/query?"+paramsStr;
        String s = Http.get(url);
        req.setResponseBody(s);
        return req;

    }

    @Override
    public String getKey(WxbotRepRes req) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String key = req.getRequestBody()+today;
        return MD5Encoder.encode(key.getBytes());
    }
    public static String urlencode(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
