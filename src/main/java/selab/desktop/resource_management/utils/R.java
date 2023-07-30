package selab.desktop.resource_management.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//通用返回结果类
@Data
@NoArgsConstructor
@AllArgsConstructor

public class R<T> implements Serializable {


    @Schema(description = "编码：1成功，0和其它数字为失败")
    private Integer code; //编码：1成功，0和其它数字为失败

    @Schema(description = "错误信息")
    private String msg; //错误信息

    @Schema(description = "数据")
    private T data; //数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

}
