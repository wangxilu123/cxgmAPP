package com.cxgm.common;

public class RSResult {

	private String code;
	private String message;
	private Object result;
	private String status;

	public RSResult() {
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "RSResult [result=" + result + ", message=" + message +",code="+code+ "]";
	}

}
