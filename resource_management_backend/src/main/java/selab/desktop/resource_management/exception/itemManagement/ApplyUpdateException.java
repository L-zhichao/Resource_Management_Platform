package selab.desktop.resource_management.exception.itemManagement;

import selab.desktop.resource_management.exception.ServiceException;

/**
 * @className: ApplyUpdateException
 * @author: lizhichao
 * @description: TODO
 * @date: 2023/08/03 17:33
 * @version: 1.0
 */
public class ApplyUpdateException extends ServiceException {
    public ApplyUpdateException() {
    }

    public ApplyUpdateException(String message) {
        super(message);
    }

    public ApplyUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplyUpdateException(Throwable cause) {
        super(cause);
    }

    public ApplyUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
