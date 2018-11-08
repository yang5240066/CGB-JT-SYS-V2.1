package com.jt.common.vo;

import java.io.Serializable;

/**
 * 通过此对象封装服务端控制层要向客户端响应的数据(正确或错误的数据)
 * @author UID
 *
 */
public class JsonResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
//Result, R
	
//	400 客户端向服务端传递的数据格式不对(请求)
//	404 资源没找到,请求路径有问题
//	405 请求方式映射有问题
//	406 服务器返回的数据类型在客户端不匹配(响应)
//	415 客户端向服务端传递的mediaType不对
	
	//状态码:1表示正确,0表示错误
	private Integer state=1;
	//状态码对应的消息描述
	private String message;
	//服务端要客户端呈现的具体数据,例如可以使一个查询的结果
	private Object data;
	
	
	
	public JsonResult() {
	}

	public JsonResult(String message) {
		this.message = message;
	}
	
	public JsonResult(Throwable e) {
		this.state = 0;
		this.message = e.getMessage();
	}

	public JsonResult(Object data) {
		this.data = data;
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	
	
	
}
