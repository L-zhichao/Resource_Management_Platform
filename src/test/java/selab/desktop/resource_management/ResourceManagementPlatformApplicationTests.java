package selab.desktop.resource_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;
import selab.desktop.resource_management.fundManagement.controller.FundsController;
import selab.desktop.resource_management.fundManagement.domain.Vo.FundsVo;
import selab.desktop.resource_management.fundManagement.service.FundsService;
import selab.desktop.resource_management.fundManagement.utils.R;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ResourceManagementPlatformApplicationTests {
    @Autowired
    private FundsService fundsService;

    @Test
    public void testAll() {
        List<FundsVo> list = fundsService.list();
        System.out.println(list);
    }

}
