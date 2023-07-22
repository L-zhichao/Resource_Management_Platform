package selab.desktop.resource_management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("selab.desktop.resource_management.itemManagement.mapper")
public class ResourceManagementPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceManagementPlatformApplication.class, args);
    }

}
