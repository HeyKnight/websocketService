package com.ancun.controller;

import com.ancun.pojo.CheckPhpSessionIdOnlineInput;
import com.ancun.pojo.CheckUserTelOnlineInput;
import com.ancun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关操作Controller
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月15日 16:29
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 检查用户是否在线
     * @param input  用户号码input
     * @return  online在线 or offline不在线
     */
    @RequestMapping(value = "/checkUserTelOnline", method = RequestMethod.POST)
    public @ResponseBody String checkUserTelOnline(@RequestBody CheckUserTelOnlineInput input) {
        return userService.checkUserTelOnline(input.getUserTel());
    }

    /**
     * 校验phpSessionId是否在线
     * @param input  用户号码and用户登录后的phpSessionId
     * @return  online在线 or offline不在线
     */
    @RequestMapping(value = "/checkPhpSessionIdOnline", method = RequestMethod.POST)
    public @ResponseBody String checkPhpSessionIdOnline(@RequestBody CheckPhpSessionIdOnlineInput input) {
        return userService.checkPhpSessionIdOnline(input.getUserTel(), input.getPhpSessionId());
    }

    /**
     * 清除用户在线信息
     * @param input  用户号码input
     */
    @RequestMapping(value = "/removeUserOnline", method = RequestMethod.POST)
    public void removeUserOnline(@RequestBody CheckUserTelOnlineInput input) {
        userService.removeUserOnline(input.getUserTel());
    }
}
