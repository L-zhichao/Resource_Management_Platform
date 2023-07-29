package selab.desktop.resource_management.userManagement.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
