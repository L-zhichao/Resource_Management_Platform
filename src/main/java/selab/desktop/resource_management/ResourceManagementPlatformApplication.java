package selab.desktop.resource_management;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = {"selab.desktop.resource_management.itemManagement.mapper", "selab.desktop.resource_management.userManagement.mapper"})
@SpringBootApplication
@Slf4j
public class ResourceManagementPlatformApplication {

    public static void main(String[] args) {
        log.info("程序启动");
        SpringApplication.run(ResourceManagementPlatformApplication.class, args);
    }

}
