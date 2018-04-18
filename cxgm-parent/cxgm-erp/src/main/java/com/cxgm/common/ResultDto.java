package com.cxgm.common;

import java.io.Serializable;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResultDto<T> implements Serializable{
    private static final long serialVersionUID = -343393396153859098L;
    /** 错误码*/
    private Integer code;

    /** 提示信息*/

    private String msg;

    /** 具体内容 */
    private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResultDto() {
        super();
    }
	
    public ResultDto(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    

    public ResultDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public ResultDto(HttpStatus status, String msg) {
        this.code = status.value();
        this.msg = msg;
    }
    
    public ResultDto(HttpStatus status, String msg, T data) {
        this.code = status.value();
        this.msg = msg;
        this.data = data;
    }
    
    public ResultDto(String code, String msg) {
        this.code = Integer.parseInt(code);
        this.msg = msg;
    }
    
    public ResultDto(String code, String message, T data) {
        this.code = Integer.parseInt(code);
        this.msg = message;
        this.data = data;
    }
    
    public ResultDto(T data) {
        this.code = HttpStatus.OK.value();
        this.msg = "success";
        this.data = data;
    }
    
    public static ResultDto<String> ok(String msg) {
        return new ResultDto<String>(HttpStatus.OK.value(), msg);
    }
    
    public static ResultDto<Object> ok(String msg,Object data) {
        return new ResultDto<Object>(HttpStatus.OK.value(), msg, data);
    }
    
    public static ResultDto<String> error(String msg) {
        return new ResultDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }
    
    public static ResultDto<String> addUpdateCheckSuccess() {
        return new ResultDto<String>(HttpStatus.OK.value(), "确定成功");
    }

    public static ResultDto<String> addDeleteSuccess() {
        return new ResultDto<String>(HttpStatus.OK.value(), "删除成功");
    }

    public static ResultDto<String> addOperationSuccess() {
        return new ResultDto<String>(HttpStatus.OK.value(), "操作成功");
    }

    public static ResultDto<String> processError() {
        return new ResultDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "后端服务处理失败");
    }

    public static ResultDto<String> processError(String message) {
        return new ResultDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public static ResponseEntity OK() {
        return new ResponseEntity(addOperationSuccess(),HttpStatus.OK);
    }
    
    public static ResponseEntity OK(ResultDto dto) {
        return new ResponseEntity(dto, HttpStatus.OK);
    }
    public static ResponseEntity OK(Map<String,Object> map) {
        return new ResponseEntity(map, HttpStatus.OK);
    }
    public static ResponseEntity INTERNAL_SERVER_ERROR() {
        return new ResponseEntity(processError(), HttpStatus.OK);
    }

    public static ResponseEntity INTERNAL_SERVER_ERROR(String message) {
        return new ResponseEntity(processError(message), HttpStatus.OK);
    }

    public static ResponseEntity BAD_REQUEST(String message) {
        return new ResponseEntity(processError(message), HttpStatus.BAD_REQUEST);
    }
}
