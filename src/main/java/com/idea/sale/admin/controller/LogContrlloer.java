package com.idea.sale.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idea.sale.admin.service.LogService;
import com.idea.sale.admin.vo.AndroidLogEntity;
import com.idea.sale.admin.vo.IOSLogEntity;
import com.idea.sale.admin.vo.OtherSystemLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LogContrlloer {
	@Autowired
	private LogService logService;
	// android
	private static final Logger info = LoggerFactory.getLogger("appinter");
	private static final Logger other = LoggerFactory.getLogger("other");
	// ios
	private static final Logger iosinfo = LoggerFactory.getLogger("iosappinter");
	// error
	private static final Logger error = LoggerFactory.getLogger("apperror");
	private ObjectMapper mapper = new ObjectMapper();

	/*@RequestMapping("/aaa")
	public String testLog() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "zhangsan");
		map.put("age", 10);
		map.put("sex", "nan");
		try {
			iosinfo.info(mapper.writeValueAsString(map));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "llllllllll";
	}*/

	/**
	 * desc:接收到app端发送的日志文件，然后放入flume author:zhangyong date:2018年11月29日下午2:45:33
	 * return:JSONObject version 1.0
	 */
	@RequestMapping(value = "/androld/log")
	public String sendAppInfoLog(HttpServletRequest request) {
		String re = null;
		try {
			Map<String, String> result = new HashMap<>();
			List<AndroidLogEntity> list = logService.SendAppLogToFlume(request);
			for (int i = 0; i < list.size(); i++) {
				AndroidLogEntity json = list.get(i);
				info.info(mapper.writeValueAsString(json));
			}
			result.put("flag", "0");
			re = new ObjectMapper().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return re;
	}

	@RequestMapping(value = "/ios/log")
	public String sendIosAppInfoLog(HttpServletRequest request) {
		String re = null;
		try {
			Map<String, String> result = new HashMap<>();
			List<IOSLogEntity> list = logService.SendIosAppLogToFlume(request);
			for (int i = 0; i < list.size(); i++) {
				IOSLogEntity json = list.get(i);
				iosinfo.info(mapper.writeValueAsString(json));
			}
			result.put("flag", "0");
			re = new ObjectMapper().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return re;
	}


	/**
	 * desc:接收其它系统的接口日志 author:zhangyong date:2018年11月29日下午4:06:33
	 * return:JSONObject version 1.0
	 */
	@GetMapping("/web/log")
	public String sendSystemInfoLog(HttpServletRequest request) {
		List<OtherSystemLogEntity> jsonObject = logService.SendSystemLogToFlume(request);
		other.info("info", jsonObject);
		return "";
	}

}
