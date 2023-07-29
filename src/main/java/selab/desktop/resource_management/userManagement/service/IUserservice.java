package selab.desktop.resource_management.userManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.userManagement.domain.User;
import selab.desktop.resource_management.userManagement.domain.vo.UserReturn;
import selab.desktop.resource_management.userManagement.domain.vo.UserVo;

@Service
public interface IUserservice extends IService<User> {

    void verifyUsername(String username);


    /**
     *  注册用户
     * @param userVo
     */
    void register(UserVo userVo);


    /**
     *  用户登录
     * @param username  用户名
     * @param password  密码
     * @return   当前用户
     */

    UserReturn login(String username, String password);

}
