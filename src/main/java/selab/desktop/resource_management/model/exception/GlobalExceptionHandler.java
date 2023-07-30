package selab.desktop.resource_management.model.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import selab.desktop.resource_management.utils.R;

import java.sql.SQLIntegrityConstraintViolationException;

//全局异常处理
@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
@ResponseBody
public class GlobalExceptionHandler {
    //数据库中unique约束报错
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler1(SQLIntegrityConstraintViolationException e) {
        //解读异常信息 向前端返回原因
        String eMessage = e.getMessage();
        log.info(eMessage);
        if (eMessage.contains("Duplicate entry")) {
            String[] strings = eMessage.split(" ");
            String rMessage = strings[2] + "重复";
            return R.error(rMessage);
        }
        return R.error("数据库错误");
    }
}
