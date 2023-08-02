package selab.desktop.resource_management.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T>{

    public final static Integer SUCCESS = 200;
    @Schema(description = "状态码")
    private Integer status;
    @Schema(description = "错误信息")
    private String message;
    @Schema(description = "数据")
    private T data;
}
