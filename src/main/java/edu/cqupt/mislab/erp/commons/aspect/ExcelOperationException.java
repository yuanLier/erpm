package edu.cqupt.mislab.erp.commons.aspect;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;

/**
 * @author yuanyiwen
 * @create 2019-10-22 10:32
 * @description Excel数据导入异常；包括文件上传异常与数据转换异常
 */
public class ExcelOperationException extends RuntimeException {

    private WebResponseVo.ResponseStatus responseStatus;

    public ExcelOperationException() {
        super();
    }

    public ExcelOperationException(WebResponseVo.ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ExcelOperationException(WebResponseVo.ResponseStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public WebResponseVo.ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
