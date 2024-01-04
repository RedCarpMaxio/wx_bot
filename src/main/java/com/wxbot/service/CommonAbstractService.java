package com.wxbot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxbot.domain.WxbotRepRes;
import com.wxbot.mapper.WxbotRepResMapper;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class CommonAbstractService {

    @Resource
    private WxbotRepResMapper mapper;

    // 模板方法，定义了算法的骨架
    public final String handler(WxbotRepRes req) {
        String key = getKey(req);
        req.setKeywords(key);
        if (queryLocal(req)) {
            return req.getResponseBody();
        }
        WxbotRepRes wxbotRepRes = queryOut(req);
        insert(wxbotRepRes);
        additionalStep(); // 可选的额外步骤
        return req.getResponseBody();
    }

    // 本地查询，私有方法，子类无需修改
    private boolean queryLocal(WxbotRepRes req) {

        WxbotRepRes wxbotRepRes = mapper.selectOne(new LambdaQueryWrapper<WxbotRepRes>().eq(WxbotRepRes::getKeywords, req.getKeywords()).orderByDesc(WxbotRepRes::getInsertTime));
        if (wxbotRepRes != null) {
            req.setResponseBody(wxbotRepRes.getResponseBody());
            return true;
        }
        return false;
    }

    // 抽象方法，子类必须实现
    public abstract WxbotRepRes queryOut(WxbotRepRes req);

    // 可选的额外步骤，子类可以选择性覆盖
    protected void additionalStep() {
        // 默认实现，可以留空
    }

    private void insert(WxbotRepRes wxbotRepRes) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        wxbotRepRes.setInsertTime(now);
        mapper.insert(wxbotRepRes);
    }

    public abstract String getKey(WxbotRepRes req);
}
