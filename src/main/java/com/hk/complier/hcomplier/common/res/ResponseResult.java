package com.hk.complier.hcomplier.common.res;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 数据响应对象
 * {
 *     success：成功,
 *     code   ：响应码,
 *     message:返回信息,
 *     //响应数据
 *     data:{
 *
 *     }
 *
 * }
 *
 *
 * @author HK意境**/

@Data
@ToString
@NoArgsConstructor
public class ResponseResult<T extends Object> {


    //是否成功
    private boolean success;
    //返回码
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private T data;

    // 静态放回对象
    public static ResponseResult SuccessResponse = new ResponseResult(ResultCode.SUCCESS,"ok");
    public static ResponseResult FailResponse = new ResponseResult(ResultCode.FAIL,"failed");
    public static ResponseResult ErrorResponse = new ResponseResult(ResultCode.REMOTE_INTERFACE_ERROR,"exception");


    public ResponseResult(ResultCode code) {
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
    }

    public ResponseResult(ResultCode code, T data) {
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
        this.data = data;
    }

    public ResponseResult(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public ResponseResult setResultCode(ResultCode code){
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
        return this;
    }

    public ResponseResult setData(T data){
        this.data = data ;
        return this;
    }


    public static ResponseResult SUCCESS(){
        return new ResponseResult(ResultCode.SUCCESS);
    }

    public static ResponseResult ERROR(){
        return new ResponseResult(ResultCode.SERVER_ERROR);
    }

    public static ResponseResult FAIL(){
        return new ResponseResult(ResultCode.FAIL);
    }
}
