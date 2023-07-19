package selab.desktop.resource_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.cloudresourceManagement.domain.User;
import selab.desktop.resource_management.cloudresourceManagement.mapper.UserMapper;

@SpringBootTest
class ResourceManagementPlatformApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = userMapper.selectByUserNameAndPassword("admain"," ");
        System.out.println(user);
    }

}
