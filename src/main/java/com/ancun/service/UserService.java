package com.ancun.service;

/**
 * 用户信息接口
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月15日 17:04
 */
public interface UserService {

    /**
     * 校验用户是否在线
     * @param userTel  用户号码
     * @return online在线 or offline不在线
     */
    public String checkUserTelOnline(String userTel);

    /**
     * 校验phpSessionId是否在线
     * @param userTel  用户号码
     * @param phpSessionId  登陆的sessionId
     * @return online在线 or offline不在线
     */
    public String checkPhpSessionIdOnline(String userTel, String phpSessionId);

    /**
     * 清除用户在线信息
     * @param userTel  用户号码
     */
    public void removeUserOnline(String userTel);
}
