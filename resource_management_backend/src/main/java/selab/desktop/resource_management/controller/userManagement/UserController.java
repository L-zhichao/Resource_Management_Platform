package selab.desktop.resource_management.controller.userManagement;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.userManagement.log.UserLog;
import selab.desktop.resource_management.domain.userManagement.vo.UserLogin;
import selab.desktop.resource_management.domain.userManagement.vo.UserReturn;
import selab.desktop.resource_management.domain.userManagement.vo.UserVo;
import selab.desktop.resource_management.service.userManagement.UserLogService;
import selab.desktop.resource_management.service.userManagement.impl.UserServiceImpl;
import selab.desktop.resource_management.utils.JsonResult;

import java.util.Date;
import java.util.List;


@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final UserLogService userLogService;
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
   public JsonResult<Void> verifyUsername(@RequestParam String username){
        userServiceImpl.verifyUsername(username);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
   }

    /**
     *
     * 做登录操作

     * @param userLogin
     * @return    userVo对象
     */
    @Operation(summary = "登录模块")
    @PostMapping("/login")
 public JsonResult<UserReturn> login(@RequestBody UserLogin userLogin, HttpSession httpSession, HttpServletRequest request){
        UserReturn userReturn = userServiceImpl.login(userLogin.getUsername(), userLogin.getPassword());
        httpSession.setAttribute("name",userReturn.getName());
        httpSession.setAttribute("username",userReturn.getUsername());
        httpSession.setAttribute("userStatus",userReturn.getUserStatus());
        UserLog userLog = new UserLog();
        userLog.setUserId(userReturn.getUuid());
        String origin = request.getHeader("Origin");
        userLog.setIp(origin);
        userLog.setUsername(userLogin.getUsername());
        userLog.setLoginTime(new Date());
        userLogService.insertLog(userLog);
        JsonResult<UserReturn> jsonResult = new JsonResult<>(JsonResult.SUCCESS,null,userReturn);
        return jsonResult;
    }

    @GetMapping("/log")
    public JsonResult<List<UserLog>> log(@RequestParam Long current,@RequestParam Long size){
        List<UserLog> userLogs = userLogService.queryAll(current, size);
        JsonResult<List<UserLog>> listJsonResult = new JsonResult<>(JsonResult.SUCCESS,null,userLogs);
        return listJsonResult;
    }


}
