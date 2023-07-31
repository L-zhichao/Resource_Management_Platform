package selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Schema(description = "物品申请展示模型")
@Data
public class ApplyItemVo {
    @Schema(description = "申请人")
     @NotEmpty
    private String applyUser;
    @Schema(description = "申请内容")
     @NotEmpty
    private String content;
    @Schema(description = "申请金额")
     @Min(0)
    private Double money;
    @Schema(description = "申请状态(-1为未读，1为已读)")
    private Integer status;
    @Schema(description = "申请时间")
    private Date applyTime;
}
