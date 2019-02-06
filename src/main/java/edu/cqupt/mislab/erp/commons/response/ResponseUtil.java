package edu.cqupt.mislab.erp.commons.response;

import org.springframework.http.HttpStatus;

/**
 * 注意：这个类禁止静态导入！！！
 */
public class ResponseUtil {

    /**
     * 创建响应对象的方法
     * @param responseStatus：响应代码：1标识成功，0标识失败
     * @param msg：如果失败后将返回的错误信息
     * @param data：需要响应的数据
     */
    public static <T> ResponseVo<T> toResponseVo(HttpStatus responseStatus,String msg,T data){

        ResponseVo<T> responseVo = new ResponseVo<>();

        responseVo.setCode(responseStatus);
        responseVo.setMsg(msg == null ? responseStatus.getReasonPhrase() : msg);
        responseVo.setData(data);

        return responseVo;
    }

    public static <T> ResponseVo<T> toSuccessResponseVo(T data){

        ResponseVo<T> responseVo = new ResponseVo<>();

        responseVo.setCode(HttpStatus.OK);
        responseVo.setMsg(HttpStatus.OK.getReasonPhrase());
        responseVo.setData(data);

        return responseVo;
    }

    public static <T> ResponseVo<T> toSuccessResponseVoWithNoData(){

        return ResponseUtil.<T>toSuccessResponseVo((T) null);
    }

    public static <T> ResponseVo<T> toFailResponseVo(HttpStatus responseStatus,String msg){

        ResponseVo<T> responseVo = new ResponseVo<>();

        responseVo.setCode(responseStatus);
        responseVo.setMsg(msg == null ? responseStatus.getReasonPhrase() : msg);
        responseVo.setData((T) null);

        return responseVo;
    }

    public static <T> ResponseVo<T> toFailResponseVoWithNoMessage(HttpStatus responseStatus){

        return ResponseUtil.<T>toFailResponseVo(responseStatus,null);
    }
}