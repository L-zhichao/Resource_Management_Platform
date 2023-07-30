package selab.desktop.resource_management.fundManagement.exception;

public class IDNotFundException extends ServiceException{
    public IDNotFundException() {
        super();
    }

    public IDNotFundException(String message) {
        super(message);
    }

    public IDNotFundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IDNotFundException(Throwable cause) {
        super(cause);
    }

    protected IDNotFundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
