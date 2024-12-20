package com.cershy.linyuminiserver.configs;


import com.cershy.linyuminiserver.annotation.UserIp;
import com.cershy.linyuminiserver.annotation.Userid;
import com.cershy.linyuminiserver.utils.IpUtil;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: dwh
 **/
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Userid.class) ||
                parameter.hasParameterAnnotation(UserIp.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        if (parameter.hasParameterAnnotation(Userid.class)) {
            Map<String, Object> userinfo = (Map<String, Object>) request.getAttribute("userinfo");
            if (userinfo != null) {
                return userinfo.get("userId");
            }
        }
        if (parameter.hasParameterAnnotation(UserIp.class)) {
            String ipAddr = IpUtil.getIpAddr(request);
            if (ipAddr != null) {
                return ipAddr;
            }
        }
        return null;
    }
}
