package selab.desktop.resource_management.exception.userManagment;

import selab.desktop.resource_management.exception.ServiceException;

public class UserNotFundException extends ServiceException {
    public UserNotFundException() {
        super();
    }

    public UserNotFundException(String message) {
        super(message);
    }

    public UserNotFundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFundException(Throwable cause) {
        super(cause);
    }

    protected UserNotFundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
