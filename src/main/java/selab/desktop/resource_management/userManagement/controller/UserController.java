package selab.desktop.resource_management.userManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.userManagement.domain.vo.UserVo;
import selab.desktop.resource_management.userManagement.service.impl.UserServiceImpl;
import selab.desktop.resource_management.userManagement.utils.JsonResult;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    /**
     * 注册用户
     * @param userVo
     */
    @Operation(summary = "注册模块")
    @PostMapping("/register")
    JsonResult<Void> register(@Validated @RequestBody UserVo userVo){

        userServiceImpl.register( userVo);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }


    /**
     *
     * 做登录操作
     * @param username 用户名
     * @param password  密码
     * @return    userVo对象
     */
    @Operation(summary = "登录模块")
    @PostMapping("/login")
 JsonResult<UserVo> login(@RequestParam String username, String password, HttpSession httpSession){
        System.out.println(password);
        UserVo userVo = userServiceImpl.login(username, password);
        httpSession.setAttribute("name",userVo.getName());
        httpSession.setAttribute("username",userVo.getUsername());
        httpSession.setAttribute("userStatus",userVo.getUserStatus());
        JsonResult<UserVo> jsonResult = new JsonResult<>(JsonResult.SUCCESS,null,userVo);
        return jsonResult;
    }


}
