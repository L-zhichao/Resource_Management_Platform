package selab.desktop.resource_management.service.userManagement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.userManagement.User;
import selab.desktop.resource_management.domain.userManagement.vo.UserVo;
import selab.desktop.resource_management.domain.userManagement.DTO.UserDTO;

@Service
public interface IUserservice extends IService<User> {

    void verifyUsername(String username);


    /**
     *  注册用户
     * @param userDTO
     */
    void register(UserDTO userDTO);


    /**
     *  用户登录
     * @param username  用户名
     * @param password  密码
     * @return   当前用户
     */

    UserVo login(String username, String password);

}
