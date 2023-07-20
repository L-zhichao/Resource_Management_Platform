package selab.desktop.resource_management.userManagement.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonResult<T>{

    public final static Integer SUCCESS = 200;
    private Integer status;
    private String message;
    private T data;
}
