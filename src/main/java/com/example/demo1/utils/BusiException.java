package com.example.demo1.utils;

public class BusiException extends RuntimeException {

	private static final long serialVersionUID = -274762633056259205L;

	private int code;
	
	private String msg;
	
	public BusiException(int errcode, String errmsg, Exception e) {
		super();
		this.code = errcode;
		this.msg = errmsg;
	}

	public BusiException(int errcode, String errmsg) {
		super();
		this.code = errcode;
		this.msg = errmsg;
	}
	
	public BusiException(int errcode) {
		super();
		this.code = errcode;
		this.msg = String.valueOf("---------");
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
