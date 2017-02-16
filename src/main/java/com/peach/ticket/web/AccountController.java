package com.peach.ticket.web;

import com.peach.ticket.domain.User;
import com.peach.ticket.service.UserService;
import com.peach.ticket.web.dto.UserDTO;
import com.peach.ticket.web.response.ResponseCode;
import com.peach.ticket.web.response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by hgwang on 2017/2/15.
 */
@RestController
@RequestMapping(value = "/api/account")
public class AccountController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseVo login(
            HttpServletRequest request,
            @RequestParam String username,
            @RequestParam String password
    ) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            password = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {

            throw new Exception("MD5加密出现错误");
        }

        User user = this.userService.findByUserName(username);
        if (user == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "用户名不存在");
        }
        if(user.getStatus() != 1){
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "用户未启用，请联系管理员");
        }
        if (!user.getPassword().equals(password)) {
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "密码错误");
        }

        request.getSession().setAttribute("User", user);

        return new ResponseVo(new UserDTO(user));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseVo login(HttpServletRequest request) {

        request.getSession().removeAttribute("User");

        return new ResponseVo(ResponseCode.OK, "退出成功");
    }
}
