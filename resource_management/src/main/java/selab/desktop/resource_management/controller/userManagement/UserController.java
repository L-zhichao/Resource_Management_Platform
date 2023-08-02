package selab.desktop.resource_management.controller.userManagement;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.userManagement.vo.UserReturn;
import selab.desktop.resource_management.domain.userManagement.vo.UserVo;
import selab.desktop.resource_management.service.userManagement.impl.UserServiceImpl;
import selab.desktop.resource_management.utils.JsonResult;

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
    public JsonResult<Void> register(@Validated @RequestBody UserVo userVo){

        userServiceImpl.register( userVo);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    /**
     * 验证用户名是否已经存在
     * @param username  用户名
     * @return
     */
    @Operation(summary = "用户名验证模块")
    @PostMapping("/user/verify")
   public JsonResult<Void> verifyUsername(String username){
        userServiceImpl.verifyUsername(username);
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
 public JsonResult<UserReturn> login(@RequestParam String username, String password, HttpSession httpSession){
        System.out.println(password);
       UserReturn userReturn = userServiceImpl.login(username, password);
        httpSession.setAttribute("name",userReturn.getName());
        httpSession.setAttribute("username",userReturn.getUsername());
        httpSession.setAttribute("userStatus",userReturn.getUserStatus());
        httpSession.setAttribute("token",userReturn.getToken());
        JsonResult<UserReturn> jsonResult = new JsonResult<>(JsonResult.SUCCESS,null,userReturn);
        return jsonResult;
    }


}
