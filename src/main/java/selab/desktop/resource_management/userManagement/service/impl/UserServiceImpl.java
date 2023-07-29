package selab.desktop.resource_management.userManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import selab.desktop.resource_management.userManagement.domain.User;
import selab.desktop.resource_management.userManagement.domain.vo.UserReturn;
import selab.desktop.resource_management.userManagement.domain.vo.UserVo;
import selab.desktop.resource_management.userManagement.exception.InsertException;
import selab.desktop.resource_management.userManagement.exception.PasswordNotMatchException;
import selab.desktop.resource_management.userManagement.exception.UserNotFundException;
import selab.desktop.resource_management.userManagement.exception.UsernameDuplicatedException;
import selab.desktop.resource_management.userManagement.mapper.UserMapper;
import selab.desktop.resource_management.userManagement.service.IUserservice;

import java.util.Date;
import java.util.Random;
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
    public void register(UserVo userVo) {
        String username = userVo.getUsername();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if(user != null){
            throw new UsernameDuplicatedException("当前用户名已存在");
        }
        String salt = UUID.randomUUID().toString().toUpperCase();
        String oldPassword = userVo.getPassword();
        String password = getMD5Password(oldPassword,salt);
        User newUser = userVoToUser(userVo,password,salt);
        Integer rows = userMapper.insert(newUser);
        if(rows != 1){
            throw new InsertException("增加用户未知异常");
        }
    }

    @Override
    public UserReturn login(String username, String password) {
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
       UserReturn userReturn = userToUserReturn(user);
        return userReturn;
    }
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }

        return password;
    }
    private User userVoToUser(UserVo userVo,String password,String salt){

        User user = new User();
        user.setName(userVo.getName());
        user.setUsername(userVo.getUsername());
        user.setPassword(password);
        user.setEmail(userVo.getEmail());
        user.setRegistrationTime(userVo.getRegistrationTime());
        user.setUserStatus(User.ORDINARY_USER);
        user.setSalt(salt);
       return user;
    }
private UserReturn userToUserReturn(User user){

        UserReturn userReturn = new UserReturn();
        userReturn.setUuid(user.getUserId());
        userReturn.setName(user.getName());
        userReturn.setUsername(user.getUsername());
        userReturn.setUserStatus(user.getUserStatus());
        userReturn.setEmail(user.getEmail());
        userReturn.setToken(getToken());
        return userReturn;
}
  private String getToken(){
      long timestamp = System.currentTimeMillis();
      String token = timestamp + UUID.randomUUID().toString();
      return token;
  }

}
