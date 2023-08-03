package selab.desktop.resource_management.exception.userManagment;

import selab.desktop.resource_management.exception.ServiceException;

/**
 * @className: UserLogInsertException
 * @author: lizhichao
 * @description: TODO
 * @date: 2023/08/03 11:39
 * @version: 1.0
 */
public class UserLogInsertException extends ServiceException {
    public UserLogInsertException() {
    }

    public UserLogInsertException(String message) {
        super(message);
    }

    public UserLogInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLogInsertException(Throwable cause) {
        super(cause);
    }

    public UserLogInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
