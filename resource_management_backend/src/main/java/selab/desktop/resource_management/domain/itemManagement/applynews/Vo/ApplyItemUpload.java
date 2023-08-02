package selab.desktop.resource_management.domain.itemManagement.applynews.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
@Data
@Schema(description = "申请上传模板")
public class ApplyItemUpload {
    @Schema(description = "申请人名字")
    @NotEmpty
    private String name;

    @Schema(description = "申请内容")
    @NotEmpty
    private String content;
    @Schema(description = "申请金额")
    @Min(0)
    private Double money;
    @Schema(description = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyTime;

}
