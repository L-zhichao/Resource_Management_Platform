package selab.desktop.resource_management;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ResourceManagementPlatformApplication{

	public static void main(String[] args) {
		SpringApplication.run(ResourceManagementPlatformApplication.class, args);
	}

}
