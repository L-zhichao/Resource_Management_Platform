package selab.desktop.resource_management.domain.itemManagement.applynews.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Schema(description = "物品回应申请模板")
@Data
public class ResponseItemVo {
    @Schema(description = "物品申请信息id")
    private Long applyId;
    @Schema(description = "回应人")
    private String resonseUser;
    @Schema(description = "回应信息状态(-1为未读，1为已读)")
    private Integer status;
    @Schema(description = "回应结果(同意与否2为同意，0为否)")
    private Integer result;
    @Schema(description = "说明回应结果原因")
    private String reason;
    @Schema(description = "回应时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date responseTime;

}
