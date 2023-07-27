package selab.desktop.resource_management.itemManagement.utils;



import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    @Schema(description = "状态码")
    private Integer code;
    @Schema(description = "信息")
    private String message;
    @Schema(description = "数据")
    private T data;
}
