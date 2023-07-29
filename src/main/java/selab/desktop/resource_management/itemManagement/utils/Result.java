package selab.desktop.resource_management.itemManagement.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer responseCode;
    private String message;
    private T data;
}
