package selab.desktop.resource_management.model.exception;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import selab.desktop.resource_management.model.exception.fundManagement.FundInsertException;
import selab.desktop.resource_management.model.exception.fundManagement.IDNotFundException;
import selab.desktop.resource_management.model.exception.itemManagement.ItemExistsException;
import selab.desktop.resource_management.model.exception.userManagment.PasswordNotMatchException;
import selab.desktop.resource_management.model.exception.userManagment.UserInsertException;
import selab.desktop.resource_management.model.exception.userManagment.UserNotFundException;
import selab.desktop.resource_management.model.exception.userManagment.UsernameDuplicatedException;
import selab.desktop.resource_management.model.utils.JsonResult;

import org.springframework.validation.BindException;

@RestControllerAdvice
public class ExceptionHandle {


    /**
     *   解决注册用户参数验证异常
     *   * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public JsonResult<String> validExceptionHandle(BindException e){
       JsonResult<String> jsonResult = new JsonResult<>();
       jsonResult.setStatus(40001);
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        // e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        jsonResult.setMessage(fieldError.getDefaultMessage());
       jsonResult.setData(fieldError.getField());
        return jsonResult;
    }

   @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> serviceExceptionHandle(ServiceException e){
       if(e instanceof UsernameDuplicatedException){
           return new JsonResult<>(40002,"当前用户名已存在",null);
       } else if (e instanceof UserNotFundException) {
           return new JsonResult<>(40003,"当前用户不存在",null);
       } else if (e instanceof PasswordNotMatchException) {
           return new JsonResult<>(40004,"密码错误",null);
       } else if (e instanceof UserInsertException) {
           return new JsonResult<>(50001,"用户增加未知异常",null);
       } else if (e instanceof IDNotFundException) {
           return new JsonResult<>(40005, "未找到以下ID", null);
       }else if (e instanceof FundInsertException) {
            return new JsonResult<>(50002, "增加资金异常", null);
       } else if (e instanceof ItemExistsException) {
           return new JsonResult<>(40006,"物品已经存在异常",null);
       }

       return new JsonResult<>(50005,"未知异常",null);
   }


    }
