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

@SpringBootTest
class ResourceManagementPlatformApplicationTests {
    @Autowired
    private FundsService fundsService;

    @Test
    public void add01() {
        FundsController fundsController = new FundsController();
        FundsVo fundsVo = new FundsVo();
        Integer integer = 1;
        long l = integer.longValue();


        fundsVo.setId(null);
        fundsVo.setAsset("股票");
        fundsVo.setJudge(1);
        fundsVo.setAssetValue("10000");
        fundsVo.setUpdateTime(null);
        fundsController.add(fundsVo);
    }

}
