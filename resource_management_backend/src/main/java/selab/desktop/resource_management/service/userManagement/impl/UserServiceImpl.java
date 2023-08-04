package selab.desktop.resource_management.service.userManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import selab.desktop.resource_management.domain.userManagement.User;
import selab.desktop.resource_management.domain.userManagement.vo.UserVo;
import selab.desktop.resource_management.domain.userManagement.DTO.UserDTO;
import selab.desktop.resource_management.exception.userManagment.PasswordNotMatchException;
import selab.desktop.resource_management.exception.userManagment.UserInsertException;
import selab.desktop.resource_management.exception.userManagment.UserNotFundException;
import selab.desktop.resource_management.exception.userManagment.UsernameDuplicatedException;
import selab.desktop.resource_management.mapper.userManagement.UserMapper;
import selab.desktop.resource_management.service.userManagement.IUserservice;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserservice {



    private final UserMapper userMapper;


    @Override
    public void verifyUsername(String username) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if(user != null){
            throw new UsernameDuplicatedException("当前用户名已存在");
        }
    }

   @Override
    public void register(UserDTO userDTO) {
        String username = userDTO.getUsername();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if(user != null){
            throw new UsernameDuplicatedException("当前用户名已存在");
        }
        String salt = UUID.randomUUID().toString().toUpperCase();
        String oldPassword = userDTO.getPassword();
        String password = getMD5Password(oldPassword,salt);
        User newUser = userVoToUser(userDTO,password,salt);
        Integer rows = userMapper.insert(newUser);
        if(rows != 1){
            throw new UserInsertException("增加用户未知异常");
        }
    }

    @Override
    public UserVo login(String username, String password) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if(user == null){
            throw new UserNotFundException("当前用户不存在");
        }
        String loginPassword = getMD5Password(password,user.getSalt());
        String reallyPassword = user.getPassword();
        if(!reallyPassword.equals(loginPassword)){
            throw new PasswordNotMatchException("密码错误");
        }
       UserVo userVo = userToUserReturn(user);
        return userVo;
    }
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }

        return password;
    }
    private User userVoToUser(UserDTO userDTO, String password, String salt){

        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(password);
        user.setEmail(userDTO.getEmail());
        user.setRegistrationTime(userDTO.getRegistrationTime());
        user.setUserStatus(User.ORDINARY_USER);
        user.setSalt(salt);
       return user;
    }
private UserVo userToUserReturn(User user){

        UserVo userVo = new UserVo();
        userVo.setUuid(user.getUserId());
        userVo.setName(user.getName());
        userVo.setUsername(user.getUsername());
        userVo.setUserStatus(user.getUserStatus());
        userVo.setEmail(user.getEmail());
        userVo.setToken(getToken());
        return userVo;
}
  private String getToken(){
      long timestamp = System.currentTimeMillis();
      String token = timestamp + UUID.randomUUID().toString();
      return token;
  }

}
