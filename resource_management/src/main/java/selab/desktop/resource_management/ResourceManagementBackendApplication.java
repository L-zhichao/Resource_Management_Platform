package selab.desktop.resource_management;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = "selab.desktop.resource_management.mapper")
@SpringBootApplication
public class ResourceManagementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceManagementBackendApplication.class, args);
	}

}
