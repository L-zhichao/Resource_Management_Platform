package selab.desktop.resource_management.controller.userManagement;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.userManagement.log.UserLog;
import selab.desktop.resource_management.domain.userManagement.DTO.UserLogin;
import selab.desktop.resource_management.domain.userManagement.vo.UserVo;
import selab.desktop.resource_management.domain.userManagement.DTO.UserDTO;
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
     * @param userDTO
     */
    @Operation(summary = "注册模块")
    @PostMapping("/register")
    public JsonResult<Void> register(@Validated @RequestBody UserDTO userDTO){

        userServiceImpl.register(userDTO);
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
 public JsonResult<UserVo> login(@RequestBody UserLogin userLogin, HttpSession httpSession, HttpServletRequest request){
        UserVo userVo = userServiceImpl.login(userLogin.getUsername(), userLogin.getPassword());
        httpSession.setAttribute("name", userVo.getName());
        httpSession.setAttribute("username", userVo.getUsername());
        httpSession.setAttribute("userStatus", userVo.getUserStatus());
        UserLog userLog = new UserLog();
        userLog.setUserId(userVo.getUuid());
        String origin = request.getHeader("Origin");
        userLog.setIp(origin);
        userLog.setUsername(userLogin.getUsername());
        userLog.setLoginTime(new Date());
        userLogService.insertLog(userLog);
        JsonResult<UserVo> jsonResult = new JsonResult<>(JsonResult.SUCCESS,null, userVo);
        return jsonResult;
    }

    @GetMapping("/log")
    public JsonResult<List<UserLog>> log(@RequestParam Long current,@RequestParam Long size){
        List<UserLog> userLogs = userLogService.queryAll(current, size);
        JsonResult<List<UserLog>> listJsonResult = new JsonResult<>(JsonResult.SUCCESS,null,userLogs);
        return listJsonResult;
    }
    @GetMapping("/count")
   public JsonResult<Long> queryLogCount(){
        Long count = userLogService.queryForCount();
        JsonResult<Long> longJsonResult = new JsonResult<>(JsonResult.SUCCESS, null, count);
        return longJsonResult;
    }

}
