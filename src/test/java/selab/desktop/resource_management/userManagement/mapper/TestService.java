package selab.desktop.resource_management.userManagement.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.userManagement.domain.vo.UserVo;
import selab.desktop.resource_management.userManagement.service.impl.UserServiceImpl;

@SpringBootTest
public class TestService {

    @Autowired
    private UserServiceImpl userService;
    @Test
    public void testInsert(){
        UserVo userVo = new UserVo();
        userVo.setName("admain");
        userVo.setUsername("admain");
        userVo.setPassword("abc123");
        userVo.setUserStatus(0);
        userVo.setEmail("123@email.com");
        userService.register(userVo);
    }
}
