package com.httq.dto;

import java.util.Date;

public class BaseResponse<T> {
	private Integer status;
	private String msg;
	private T      data;
	private Long timestamp;

	public BaseResponse() {
		timestamp = new Date().getTime();
		status    = 0;
		msg       = "";
	}

	public BaseResponse(T data) {
		this.data = data;
	}

	public BaseResponse(T data, String msg, Integer status) {
		this.status = status;
		this.msg    = msg;
		this.data   = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
