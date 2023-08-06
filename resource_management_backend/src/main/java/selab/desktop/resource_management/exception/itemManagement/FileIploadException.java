package selab.desktop.resource_management.exception.itemManagement;

/**
 * @className: FileIploadException
 * @author: lizhichao
 * @description: TODO
 * @date: 2023/08/05 18:38
 * @version: 1.0
 */
public class FileIploadException extends RuntimeException{
    public FileIploadException() {
    }

    public FileIploadException(String message) {
        super(message);
    }

    public FileIploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileIploadException(Throwable cause) {
        super(cause);
    }

    public FileIploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
