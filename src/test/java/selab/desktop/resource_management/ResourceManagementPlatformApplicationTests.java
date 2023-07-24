package selab.desktop.resource_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.userManagement.controller.UserController;
import selab.desktop.resource_management.userManagement.domain.vo.UserVo;
import selab.desktop.resource_management.userManagement.mapper.UserMapper;
import selab.desktop.resource_management.userManagement.service.impl.UserServiceImpl;

@SpringBootTest
class ResourceManagementPlatformApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void register001(){
        UserVo domain = userService.login("test02", "123456Aa");
        System.out.println(domain);


    }


}
