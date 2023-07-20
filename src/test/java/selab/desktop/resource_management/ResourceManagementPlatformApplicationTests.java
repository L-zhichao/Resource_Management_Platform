package selab.desktop.resource_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.userManagement.mapper.UserMapper;

@SpringBootTest
class ResourceManagementPlatformApplicationTests {

    @Autowired
    private UserMapper userMapper;


}
