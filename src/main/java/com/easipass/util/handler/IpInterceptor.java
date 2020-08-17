package com.easipass.util.handler;

import com.easipass.util.entity.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * ip拦截器
 *
 * @author ZJ
 * */
@Component
public final class IpInterceptor implements HandlerInterceptor, WebMvcConfigurer {

    /**
     * ip白名单
     * */
    private static final Set<String> hostSet = new HashSet<>();

    static {
        hostSet.add("localhost:8002");
        hostSet.add("192.168.12.241:10000");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String host = request.getHeader("Host");

        for (String h : hostSet) {
            if (h.equals(host)) {
                return true;
            }
        }

        response.getWriter().println(Response.ipError());

        return false;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this).addPathPatterns("/*/**");
    }

}