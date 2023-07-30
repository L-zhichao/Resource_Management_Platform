package selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
@Data
public class ResponseItemUpload {

    @Schema(description = "物品申请信息id")
    private Long applyId;

    @Schema(description = "物品申请人")
    private String username;

    @Schema(description = "回应结果(同意与否 2为同意，0为否)")
    private Integer result;
    @Schema(description = "说明回应结果原因")
    private String reason;

    @Schema(description = "回应时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date responseTime;

}
