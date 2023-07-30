package selab.desktop.resource_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.itemManagement.mapper.ApplyItemMapper;
import selab.desktop.resource_management.model.service.userManagement.impl.UserServiceImpl;

@SpringBootTest
class ResourceManagementPlatformApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ApplyItemMapper applyItemMapper;

    @Test
    public void register001(){




    }
    @Test
   void testSave(){

   }

}
