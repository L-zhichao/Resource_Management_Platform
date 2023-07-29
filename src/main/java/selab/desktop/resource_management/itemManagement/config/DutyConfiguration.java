package selab.desktop.resource_management.itemManagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class DutyConfiguration {
    @Value("${}selab.handle.apply")
    public static String ApplyHead;
}
