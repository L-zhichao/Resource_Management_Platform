package selab.desktop.resource_management.cloudresourceManagement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selab.desktop.resource_management.cloudresourceManagement.domain.User;
import selab.desktop.resource_management.cloudresourceManagement.domain.vo.UserVo;
import selab.desktop.resource_management.cloudresourceManagement.service.IUserservice;
import selab.desktop.resource_management.cloudresourceManagement.service.impl.UserServiceImpl;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    /**
     * 注册用户
     * @param userVo
     */
    @PostMapping("/register")
    void register( UserVo userVo){

        userServiceImpl.register(userVo);
    }


    /**
     *
     * 做登录操作
     * @param username 用户名
     * @param password  密码
     * @return    userVo对象
     */
    @PostMapping("/login")
 UserVo login(String username, String password, HttpSession httpSession){
        UserVo userVo = userServiceImpl.login(username, password);
        httpSession.setAttribute("name",userVo.getName());
        httpSession.setAttribute("username",userVo.getUsername());
        httpSession.setAttribute("userStatus",userVo.getUserStatus());
        return userVo;
    }


}
