package com.renren.gota.webserver.util;

import net.sf.json.JSONObject;

import java.util.Map;

//返回客户端处理的util类
public class ServiceResultUtil {
	private ServiceResultUtil() {

	}

	public static final String RESULT_CODE_KEY = "code";
	public static final String RESULT_MSG_KEY = "msg";

	public static void addResultCode(JSONObject result, int code){
		result.put(RESULT_CODE_KEY, code);
	}

	public static void addResultMsg(JSONObject result, String msg){
		result.put(RESULT_MSG_KEY, msg);
	}

	public static void addResultCodeAndMsg(JSONObject result, int code,
			String msg) {
		result.put(RESULT_CODE_KEY, code);
		result.put(RESULT_MSG_KEY, msg);
	}

	public static void addResultCodeAndMsg(JSONObject result, int code,
			Map<Integer, String> code2msg){
		result.put(RESULT_CODE_KEY, code);
		result.put(RESULT_MSG_KEY, code2msg.get(code));
	}
}
