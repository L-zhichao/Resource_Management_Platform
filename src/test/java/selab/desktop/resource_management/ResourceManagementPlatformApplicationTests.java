package selab.desktop.resource_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.model.domain.fundManagement.Vo.FundsVo;
import selab.desktop.resource_management.model.service.fundManagement.FundsService;

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
