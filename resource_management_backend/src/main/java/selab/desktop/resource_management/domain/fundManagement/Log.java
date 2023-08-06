package selab.desktop.resource_management.domain.fundManagement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class Log {
    @Schema(description = "日志id")
    private Long id;

    @Schema(description = "日志更新时间")
    private Date updateTime;

    @Schema(description = "描述")
    private String summary;

    @Schema(description = "资产id")
    private String assetId;

    @Schema(description = "资产名")
    private String asset;

    @Schema(description = "资产价值")
    private Integer assetValue;

    @Schema(description = "判断是否可支配")
    private String judge;
}
