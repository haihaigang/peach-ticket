package com.peach.ticket.web;

import com.peach.ticket.domain.Role;
import com.peach.ticket.domain.User;
import com.peach.ticket.service.UserService;
import com.peach.ticket.web.dto.UserDTO;
import com.peach.ticket.web.response.ResponseCode;
import com.peach.ticket.web.response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgwang on 2017/2/13.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(
            @RequestParam(required = false) int id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) int status,
            @RequestParam(required = false) int roleId
    ) throws Exception {
        User oldUser = null;
        User user = null;

        if (id != 0) {
            oldUser = this.userService.findOne(id);
        }

        if (oldUser == null) {
//            TODO 新用户需要校验数据完整性
            user = new User();
            user.setId(id);
        } else {
            user = oldUser;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            password = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }

        user.setUserName(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setStatus(status);
        user.setRole(new Role(roleId));

        this.userService.save(user);
        return new ResponseVo(ResponseCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
    public ResponseVo remove(@PathVariable Long id) {
        this.userService.remove(id);

        return new ResponseVo(ResponseCode.OK, "删除成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseVo getOne(@PathVariable Long id) {
        User user = this.userService.findOne(id);
        if (user == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到数据");
        }

        return new ResponseVo(new UserDTO(user));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseVo findAll() {
        Page<User> users = this.userService.findAll();

        List<UserDTO> dtos = new ArrayList<UserDTO>();
        for (User user : users.getContent()
                ) {
            dtos.add(new UserDTO(user));
        }

        return new ResponseVo(users);
    }

    @RequestMapping(value = "/{id}/enabled", method = RequestMethod.POST)
    public ResponseVo enabled(@PathVariable Long id, @RequestParam int status) {
        User user = this.userService.findOne(id);
        if (user == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到数据");
        }

        user.setStatus(status);

        this.userService.save(user);

        return new ResponseVo(ResponseCode.OK, "操作成功");
    }
}
