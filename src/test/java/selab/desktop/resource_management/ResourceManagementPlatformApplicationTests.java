package selab.desktop.resource_management;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;
import selab.desktop.resource_management.fundManagement.controller.FundsController;
import selab.desktop.resource_management.fundManagement.domain.Vo.FundsVo;
import selab.desktop.resource_management.fundManagement.mapper.FundsMapper;
import selab.desktop.resource_management.fundManagement.service.FundsService;
import selab.desktop.resource_management.fundManagement.utils.R;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ResourceManagementPlatformApplicationTests {
    @Autowired
    private FundsService fundsService;
    @Autowired
    private FundsMapper fundsMapper;

    @Test
    public void testAll() {
        Page<FundsVo> page = new Page<>(1, 3);
        fundsMapper.selectPage(page,null);
        System.out.println(page);
    }

}
