package selab.desktop.resource_management.domain.userManagement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "登录成功返回对象模板")
@Data
public class UserReturn {
    @Schema(description = "uuid")
    private Long uuid;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "邮件")
    private String email;
    @Schema(description = "用户状态(管理员|普通用户)")
    private Integer userStatus;

    @Schema(description = "token")
    private String token;

}
