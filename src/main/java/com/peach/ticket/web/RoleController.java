package com.peach.ticket.web;

import com.peach.ticket.domain.Role;
import com.peach.ticket.service.RoleService;
import com.peach.ticket.service.UserService;
import com.peach.ticket.web.response.ResponseCode;
import com.peach.ticket.web.response.ResponseVo;
import com.peach.ticket.web.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgwang on 2017/1/29.
 */
@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(
            @RequestParam(required = false) long id,
            @RequestParam String name,
            @RequestParam String desc
    ) {
        if (name == null || name.isEmpty()) {
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "参数错误，缺少名称");
        }

        Role oldRole = null;
        Role role = null;
        if (id != 0) {
            oldRole = this.roleService.findOne(id);
        }

        if (oldRole == null) {
//            TODO 新数据需要校验数据完整性
            role = new Role();
            role.setId(id);
        } else {
            role = oldRole;
        }

        role.setName(name);
        role.setDesc(desc);
        this.roleService.save(role);

        return new ResponseVo(ResponseCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
    public ResponseVo remove(@PathVariable Long id) {
        Role role = new Role(id);

        int count = this.userService.countByRole(role);
        if(count > 0){
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "还有关联的用户，需要先删除用户");
        }

        this.roleService.remove(id);

        return new ResponseVo(ResponseCode.OK, "删除成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseVo getSample(@PathVariable Long id) {
        Role role = this.roleService.findOne(id);

        if (role == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到当前的角色");
        }

        RoleDTO roleDTO = new RoleDTO(role);
        return new ResponseVo(roleDTO);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseVo findAll() {
        Page<Role> roles = this.roleService.findAll();

        List<RoleDTO> dtos = new ArrayList<RoleDTO>();
        for (Role role : roles.getContent()
                ) {
            dtos.add(new RoleDTO(role));
        }

        return new ResponseVo(roles);
    }
}
