package selab.desktop.resource_management.userManagement.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import selab.desktop.resource_management.userManagement.utils.JsonResult;

import org.springframework.validation.BindException;

@RestControllerAdvice
public class ExceptionHandle {


    /**
     *   解决注册用户参数验证异常
     *   * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public JsonResult<Object> validExceptionHandle(BindException e){
       JsonResult<Object> jsonResult = new JsonResult<>();
       jsonResult.setStatus(40001);
       jsonResult.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return jsonResult;
    }
}
