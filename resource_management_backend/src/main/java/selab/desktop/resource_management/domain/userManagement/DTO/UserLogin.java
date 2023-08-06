package selab.desktop.resource_management.domain.userManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserLogin {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录密码")
    private String password;
}
