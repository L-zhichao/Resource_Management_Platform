package selab.desktop.resource_management.domain.userManagement.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Schema(description = "登录成功返回对象模板")
@Data
public class UserVo {
    @Schema(description = "uuid")
    private Long uuid;
    @Schema(description = "用户名")
//    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$",message = "用户名只能为包含数字，字母，下划线的6-10个字符")
    private String username;
    @Schema(description = "密码")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$",message = "密码至少8-16个字符，至少1个大写字母，1个小写字母和1个数字，其他可以是任意字符")
    private String password;
    @Schema(description = "姓名")
//    @NotBlank(message = "姓名不能为空")
    @Length(min = 2,max = 4,message = "姓名格式错误")
    private String name;

    @Schema(description = "邮件")
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    @Schema(description = "用户状态(管理员|普通用户)")
    private Integer userStatus;

    @Schema(description = "token")
    private String token;

}
