package org.yeh.wang.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class UtilsCollect {

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        UtilsCollect.redisTemplate = redisTemplate;
    }

    /**
     * 生成Token
     * @param username 用户名
     * @return String
     */
    public static String createToken(String username, Integer expireTime) {
        String token = "";
        Map<String, String> map = Map.of("userId", username);
        Map<String, Object> header = Map.of("alg", "HS256", "typ", "JWT");
        try {
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
//                    .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
//                    .withIssuedAt(new Date())
                    .sign(Algorithm.HMAC256("secret"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        //将token存入redis
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(username, token, expireTime, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 解析Token
     * @param token token
     * @return String 用户名
     */
    public static String verifyToken(String token) {
        //解析Token
        return JWT.decode(token).getClaim("username").asString();
    }

    /**
     * 生成32位uuid
     * @return String
     */
    public static String createUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
