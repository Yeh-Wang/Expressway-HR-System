package org.yeh.wang.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.utils.UtilsCollect;

import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static StringRedisTemplate template;

    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        LoginInterceptor.template = template;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //获取请求的URL
        String url = request.getRequestURI();
//        //URL:除了登录请求外，其他的URL都进行拦截控制
//        if (url.contains("/login")) {
//            return true;
//        }
        //获取Token
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            return false;
        }
        //验证Token
        String username = UtilsCollect.verifyToken(token);
        String redisToken = template.opsForValue().get(username);
        if (redisToken == null || !redisToken.equals(token)) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(404);
            out.close();
            response.flushBuffer();
            return false;
        }
        return true;
    }
}
