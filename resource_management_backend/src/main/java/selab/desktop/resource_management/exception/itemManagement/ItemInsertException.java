package selab.desktop.resource_management.exception.itemManagement;

import selab.desktop.resource_management.exception.ServiceException;

/**
 * @className: ItemInsertException
 * @author: lizhichao
 * @description: TODO
 * @date: 2023/08/08 12:08
 * @version: 1.0
 */
public class ItemInsertException extends ServiceException {
    public ItemInsertException() {
    }

    public ItemInsertException(String message) {
        super(message);
    }

    public ItemInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemInsertException(Throwable cause) {
        super(cause);
    }

    public ItemInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
