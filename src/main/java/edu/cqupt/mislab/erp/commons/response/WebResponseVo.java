package edu.cqupt.mislab.erp.commons.response;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * WebResponseVo是Controller层向前端进行响应的视图，所有的Controller层响应都必须使用这个VO进行响应
 * @param <T>：响应数据泛型
 */
@Data
@ApiModel
public class WebResponseVo<T> {

    @Getter//响应状态码
    public enum ResponseStatus{

        /**
        200：客户端请求成功被服务端受理且办理成功，将返回该请求需要的数据
         */
        OK(200,"OK"),

        /**
        204：客户端请求成功被服务端受理且办理成功，但是并不需要返回给客户端数据
         */
        NO_CONTENT(204,"No Content"),

        /**
        400：客户端请求参数缺失、参数格式不正确、参数解析错误、参数校验不通过时
         */
        BAD_REQUEST(400,"Bad Request"),

        /**
        401：需要进行认证的时候却没有进行认证，例如修改密码、登录等
         */
        UNAUTHORIZED(401,"Unauthorized"),

        /**
        403：禁止某些操作，例如不能删除已经开始的比赛等等
         */
        FORBIDDEN(403,"Forbidden"),

        /**
        404：业务受理且办理成功，但是服务器上面不存在这个资源
         */
        NOT_FOUND(404,"Not Found"),

        /**
        500：服务端内部的未知错误，如果出现未检查的服务端运行时异常
         */
        INTERNAL_SERVER_ERROR(500,"Internal Server Error"),

        /**
        503：服务不可以，用于拥塞控制响应和停机维护
         */
        SERVICE_UNAVAILABLE(503,"Service Unavailable");

        /**
         * 响应码
         */
        private Integer code;

        /**
         * 这个响应码所对应的说明信息
         */
        private String infoMessage;

        ResponseStatus(Integer code,String infoMessage){
            this.code = code;
            this.infoMessage = infoMessage;
        }
    }

    /**
     * 忽略掉这个属性，使用自定义的方式重新组织这个返回值
     */
    @JsonIgnore
    @ApiModelProperty("请求的响应状态，具体状态意思参见该枚举注释")
    private ResponseStatus responseStatus;

    @ApiModelProperty("提示信息，即可能是成功的提示信息也可能是错误提示信息，以code值为准")
    private String msg;

    @ApiModelProperty("响应成功时的负载消息，用于标识响应结果，如果请求响应成功却没有响应结果，该值为null，响应出错该值也为null")
    private T data;

    /**
     * 解决JSON无法准确转换HttpStatus的code的问题
     */
    @JsonGetter("code")
    public int getResponseCode(){
        return responseStatus.getCode();
    }
}
