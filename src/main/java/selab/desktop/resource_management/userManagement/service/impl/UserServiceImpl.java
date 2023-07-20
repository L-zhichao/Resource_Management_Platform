package selab.desktop.resource_management.userManagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import selab.desktop.resource_management.userManagement.domain.User;
import selab.desktop.resource_management.userManagement.domain.vo.UserVo;
import selab.desktop.resource_management.userManagement.exception.InsertException;
import selab.desktop.resource_management.userManagement.exception.PasswordNotMatchException;
import selab.desktop.resource_management.userManagement.exception.UserNotFundException;
import selab.desktop.resource_management.userManagement.exception.UsernameDuplicatedException;
import selab.desktop.resource_management.userManagement.mapper.UserMapper;
import selab.desktop.resource_management.userManagement.service.IUserservice;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserservice {



    private final UserMapper userMapper;


    @Override
    public void register(UserVo userVo) {
        String username = userVo.getUsername();
        User user = userMapper.selectByUsername(username);
        if(user != null){
            throw new UsernameDuplicatedException("当前用户名已存在");
        }
        String salt = UUID.randomUUID().toString().toUpperCase();
        String oldPassword = userVo.getPassword();
        String password = getMD5Password(oldPassword,salt);
        Date registerTime = new Date();
        User newUser = userVoToUser(userVo,password,salt,registerTime);
        Integer rows = userMapper.insertUser(newUser);
        if(rows != 1){
            throw new InsertException("增加用户未知异常");
        }
    }

    @Override
    public UserVo login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if(user == null){
            throw new UserNotFundException("当前用户不存在");
        }
        String loginPassword = getMD5Password(password,user.getSalt());
        String reallyPassword = user.getPassword();
        if(!reallyPassword.equals(loginPassword)){
            throw new PasswordNotMatchException("密码错误");
        }
        UserVo userVo = userToUserVo(user);
        return userVo;
    }
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }

        return password;
    }
    private User userVoToUser(UserVo userVo,String password,String salt,Date registerTime){

        User user = new User();
        user.setName(userVo.getName());
        user.setUsername(userVo.getUsername());
        user.setPassword(password);
        user.setEmail(userVo.getEmail());
        user.setRegistrationTime(registerTime);
        user.setUserStatus(User.ORDINARY_USER);
        user.setSalt(salt);
       return user;
    }
private UserVo userToUserVo(User user){
        UserVo userVo = new UserVo();
        userVo.setName(user.getName());
        userVo.setUsername(user.getUsername());
        userVo.setUserStatus(user.getUserStatus());
        userVo.setEmail(user.getEmail());
        return userVo;
}

}
