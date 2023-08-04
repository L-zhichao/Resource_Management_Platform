package selab.desktop.resource_management.domain.itemManagement.applynews.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
@Data
public class ResponseItemDTO {

    @Schema(description = "物品申请信息id")
    private Long applyId;

    @Schema(description = "回应人姓名")
    private String responseName;
    @Schema(description = "物品申请人名字")
    private String applyName;

    @Schema(description = "回应结果(同意与否 2为同意，0为否)")
    private Integer result;
    @Schema(description = "说明回应结果原因")
    private String reason;

    @Schema(description = "回应时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date responseTime;

}
