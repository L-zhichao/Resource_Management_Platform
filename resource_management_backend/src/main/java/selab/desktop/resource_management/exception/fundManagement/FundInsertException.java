package selab.desktop.resource_management.exception.fundManagement;

import selab.desktop.resource_management.exception.ServiceException;

public class FundInsertException extends ServiceException {
    public FundInsertException() {
    }

    public FundInsertException(String message) {
        super(message);
    }

    public FundInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public FundInsertException(Throwable cause) {
        super(cause);
    }

    public FundInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
