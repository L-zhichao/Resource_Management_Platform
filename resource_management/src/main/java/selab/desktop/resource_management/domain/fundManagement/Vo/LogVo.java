package selab.desktop.resource_management.domain.fundManagement.Vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("funds_log")
@AllArgsConstructor
@NoArgsConstructor
public class LogVo {

    @Schema(description = "日志id")
    private Long id;

    @Schema(description = "日志更新时间")
    private Date updateTime;

    @Schema(description = "描述")
    private String summary;

    @Schema(description = "资产id")
    private Long assetId;

    @Schema(description = "资产名")
    private String asset;

    @Schema(description = "资产价值")
    private Long assetValue;

    @Schema(description = "判断是否可支配")
    private String judge;
}
