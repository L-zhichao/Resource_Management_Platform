package selab.desktop.resource_management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = {"selab.desktop.resource_management.itemManagement.mapper", "selab.desktop.resource_management.userManagement.mapper"})
@SpringBootApplication
public class ResourceManagementPlatformApplication {

    public static void main(String[] args) {

        SpringApplication.run(ResourceManagementPlatformApplication.class, args);
    }

}
