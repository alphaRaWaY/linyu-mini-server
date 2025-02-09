package com.cershy.linyuminiserver.service;

import cn.hutool.core.util.StrUtil;
import com.cershy.linyuminiserver.configs.LinyuConfig;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DeepSeekAiService {

    private final Cache<String, AtomicInteger> limitCache;

    @Resource
    private LinyuConfig linyuConfig;
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    DeepSeekAiService() {
        this.limitCache = Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build();
    }

    @PostConstruct
    public void init() {
        // 初始化 RestTemplate
        restTemplate = new RestTemplate();
        // 设置请求头，包括 API Key
        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + linyuConfig.getDeepSeek().getApiKey());
        headers.set("Content-Type", "application/json");
    }

    public String ask(String userId, String content) {
        AtomicInteger count = limitCache.getIfPresent(userId);
        if (count == null) {
            count = new AtomicInteger(0);
            limitCache.put(userId, count);
        }

        // 检查用户调用次数限制
        if (linyuConfig.getDeepSeek().getCountLimit() > 0
                && count.incrementAndGet() > linyuConfig.getDeepSeek().getCountLimit()) {
            return "您已经达到限制了，请24小时后再来吧~";
        }

        // 检查内容是否为空
        if (StrUtil.isBlank(content)) {
            return "内容不能为空~";
        }

        // 检查内容长度限制
        if (linyuConfig.getDeepSeek().getLengthLimit() > 0
                && content.length() > linyuConfig.getDeepSeek().getLengthLimit()) {
            return "问一些简单的问题吧~";
        }

        // 调用 DeepSeek API
        try {
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", linyuConfig.getDeepSeek().getModel());
            requestBody.put("stream", false);
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", content);
            messages.add(userMessage);
            requestBody.put("messages", messages);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    "https://api.deepseek.com/chat/completions",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );
            // 解析响应
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseEntity.getBody().get("choices");
                return (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");
            } else {
                return "DeepSeek服务异常，请稍后再试~";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "DeepSeek已离家出走了，请稍后再试~";
        }
    }
}