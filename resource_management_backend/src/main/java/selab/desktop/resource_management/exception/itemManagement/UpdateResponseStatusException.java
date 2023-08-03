package selab.desktop.resource_management.exception.itemManagement;

import selab.desktop.resource_management.exception.ServiceException;

/**
 * @className: UpdateResponseStatusException
 * @author: lizhichao
 * @description: TODO
 * @date: 2023/08/03 19:22
 * @version: 1.0
 */
public class UpdateResponseStatusException extends ServiceException {
    public UpdateResponseStatusException() {
    }

    public UpdateResponseStatusException(String message) {
        super(message);
    }

    public UpdateResponseStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateResponseStatusException(Throwable cause) {
        super(cause);
    }

    public UpdateResponseStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
