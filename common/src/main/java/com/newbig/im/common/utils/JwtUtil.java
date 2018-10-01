package com.newbig.im.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.newbig.im.common.constant.AppConstant;
import com.newbig.im.common.exception.TokenException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Slf4j
public class JwtUtil {

    static JWTVerifier verifier;
    static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256(AppConstant.SECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        verifier = JWT.require(algorithm)
                .withIssuer(AppConstant.ISSUER)
                .build(); //Reusable verifier instance
    }

    /**
     * 生成jwt token
     *
     * @param userUuid
     * @return
     * @throws IOException
     */
    public static String genToken(String mobile, Long userUuid) {
        String token = JWT.create()
                .withIssuer(AppConstant.ISSUER)
                //凌晨两点过期
                .withExpiresAt(DateTimeUtils.getDateToMidNight())
                .withClaim(AppConstant.USER_ID, userUuid)
                .withClaim(AppConstant.MOBILE, mobile)
                .sign(algorithm);
        return token;
    }

    /**
     * 验证jwt token
     *
     * @param request
     * @throws IOException
     * @throws ServletException
     */
    public static DecodedJWT verifyToken(final HttpServletRequest request) {
        final String authHeader = request.getHeader(AppConstant.TOKEN_HEADER);
        if (StringUtil.isEmpty(authHeader)) {
            throw new TokenException("Token 不存在");
        }
        try {
            DecodedJWT jwt = verifier.verify(authHeader);
            //token 最多一天+2小时 过期，即今天白天登录获取token之后 第二天凌晨过期,
            //过期之后 需要重新登陆获取token
//            if(jwt.getNotBefore().getTime()<System.currentTimeMillis()){
//                //TODO 重定向到首页 暂时只打log
//                log.error("userId:{},token expired",jwt.getClaims().get(AppConstant.USER_ID).asString());
//                throw new TokenException("token已过期,请重新登陆");
//            }
            return jwt;
        } catch (JWTVerificationException e) {
            throw new TokenException("非法访问");
        }
    }

    public static String getUserUuid(HttpServletRequest request) {
        DecodedJWT jwt = verifyToken(request);
        return jwt.getClaims().get(AppConstant.USER_ID).asString();

    }

    public static Long getUserUuid(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims().get(AppConstant.USER_ID).asLong();
        } catch (JWTVerificationException e) {
            log.error("token validate failed {}",e.getMessage());
        }
        return null;

    }
}
