package edu.cqupt.mislab.erp.commons.aspect;

/**
 * @author yuanyiwen
 * @create 2019-10-20 21:39
 * @description 企业状态异常
 */
public class EnterpriseStatusException extends RuntimeException{

    public EnterpriseStatusException() {}

    public EnterpriseStatusException(String message) {
        super(message);
    }
}
