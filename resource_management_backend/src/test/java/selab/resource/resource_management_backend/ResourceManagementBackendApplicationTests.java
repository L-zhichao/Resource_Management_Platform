package selab.resource.resource_management_backend;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.domain.userManagement.log.UserLog;
import selab.desktop.resource_management.service.userManagement.UserLogService;
import selab.desktop.resource_management.service.userManagement.impl.UserLogServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@SpringBootTest
class ResourceManagementBackendApplicationTests {

  private final UserLogServiceImpl userLogService;
	@Test
	void contextLoads() {
		IPage<UserLog> iPage = new Page<>(1,5);
		IPage<UserLog> page = userLogService.page(iPage);
		List<UserLog> records = page.getRecords();
	}

}
