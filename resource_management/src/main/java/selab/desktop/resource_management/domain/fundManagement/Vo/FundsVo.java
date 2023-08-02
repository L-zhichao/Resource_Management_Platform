package selab.desktop.resource_management.domain.fundManagement.Vo;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Date;
@Data
public class FundsVo {
    @TableId
    @Schema(description = "自增主键雪花ID")
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]$",message = "资产名称为包含数字，字母，下划线的字符")
    @Schema(description = "资产名称")
    private String asset;

    @Pattern(regexp = "^[0-9]$",message = "资产价值只能是数字")
    @Schema(description = "资产价值")
    private Long assetValue;

    @Schema(description = "判断资产是否可支配（是/否）")
    private String judge;

    @Schema(description = "最后更新时间,为当前值，填null就行")
    private Date updateTime;
}
