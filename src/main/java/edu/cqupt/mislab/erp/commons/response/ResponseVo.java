package edu.cqupt.mislab.erp.commons.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@ApiModel
public class ResponseVo<T> {

    /**
     * 忽略掉这个属性，使用自定义的方式重新组织这个返回值
     */
    @JsonIgnore
    @ApiModelProperty("请求的响应状态码，对应HTTP协议的响应码，该状态码仅仅标识请求的执行状态，即是否发生异常什么的，不标识响应结果")
    private HttpStatus code;

    @ApiModelProperty("提示信息，即可能是成功的提示信息也可能是")
    private String msg;

    @ApiModelProperty("响应成功时的负载消息，用于标识响应结果，如果请求响应成功却没有响应结果，该值为null，响应出错该值也为null")
    private T data;

    /**
     * 解决JSON无法准确转换HttpStatus的code的问题
     */
    @JsonGetter("code")
    public int getResponseCode(){
        return code.value();
    }
}
