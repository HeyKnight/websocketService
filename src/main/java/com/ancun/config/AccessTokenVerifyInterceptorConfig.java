package com.ancun.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token校验
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月20日 10:34
 */
public class AccessTokenVerifyInterceptorConfig extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate template;

    private final static Logger log = LoggerFactory.getLogger(AccessTokenVerifyInterceptorConfig.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("AccessToken executing ...");
        boolean flag = false;
        // token
        String accessToken = request.getParameter("token");
        if (!StringUtils.isEmpty(accessToken)) {

            if (template.hasKey("websocketUser:" + accessToken))
            {
                log.info("AccessToken SUCCESS ...  user:" + accessToken);
                flag = true;
            }
        }

        if (!flag) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().print("AccessToken ERROR");
        }

        return flag;
    }
}
