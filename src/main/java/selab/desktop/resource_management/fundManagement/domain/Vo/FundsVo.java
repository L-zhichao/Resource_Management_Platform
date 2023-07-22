package selab.desktop.resource_management.fundManagement.domain.Vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class FundsVo {
    @Schema(description = "主键ID")
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]$",message = "资产名称为包含数字，字母，下划线的字符")
    @Schema(description = "资产名称")
    private String asset;

    @Pattern(regexp = "^[0-9]$",message = "资产价值只能是数字")
    @Schema(description = "资产价值")
    private String assetValue;

    @Pattern(regexp = "^[01]$", message = "判断资产只能是 1 或 0")
    @Schema(description = "判断资产是否可支配（1/0）")
    private Integer judge;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "最后更新时间")
    private Date updateTime;
}
