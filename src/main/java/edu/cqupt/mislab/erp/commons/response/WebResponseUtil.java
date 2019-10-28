package edu.cqupt.mislab.erp.commons.response;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;

/**
 * @author chuyunfei
 * @description
 * @date 17:52 2019/5/3
 **/

public class WebResponseUtil {

    /**
     * 创建响应对象的基本方法
     */
    public static <T> WebResponseVo<T> toResponseVo(ResponseStatus responseStatus,String msg,T data){

        WebResponseVo<T> webResponseVo = new WebResponseVo<>();

        webResponseVo.setResponseStatus(responseStatus);
        webResponseVo.setMsg(msg == null ? responseStatus.getInfoMessage() : msg);
        webResponseVo.setData(data);

        return webResponseVo;
    }

    /**
     * 创建有响应数据的成功响应对象
     */
    public static <T> WebResponseVo<T> toSuccessResponseVoWithData(T data){

        return toResponseVo(ResponseStatus.OK,"操作成功！",data);
    }

    /**
     * 创建没有响应数据的成功响应对象
     */
    public static <T> WebResponseVo<T> toSuccessResponseVoWithNoData(){

        return toResponseVo(ResponseStatus.NO_CONTENT,"操作成功！",null);
    }

    /**
     * 创建有自定义提示信息的错误响应对象
     */
    public static <T> WebResponseVo<T> toFailResponseVoWithMessage(ResponseStatus responseStatus,String msg){

        return toResponseVo(responseStatus,msg,null);
    }

    /**
     * 创建没有自定义提示信息的错误响应对象
     */
    public static <T> WebResponseVo<T> toFailResponseVoWithNoMessage(ResponseStatus responseStatus){

        return toResponseVo(responseStatus,null,null);
    }
}