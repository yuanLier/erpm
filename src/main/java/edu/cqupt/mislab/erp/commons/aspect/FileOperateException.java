package edu.cqupt.mislab.erp.commons.aspect;

/**
 * @author yuanyiwen
 * @create 2019-10-20 12:38
 * @description 七牛云文件存储（上传或删除）异常
 */
public class FileOperateException extends RuntimeException {

    public FileOperateException() {
        super();
    }

    public FileOperateException(String message) {
        super(message);
    }
}
