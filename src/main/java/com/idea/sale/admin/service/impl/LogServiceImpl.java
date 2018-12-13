package com.idea.sale.admin.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idea.sale.admin.service.LogService;
import com.idea.sale.admin.util.ObjectUtil;
import com.idea.sale.admin.vo.AndroidLogEntity;
import com.idea.sale.admin.vo.IOSLogEntity;
import com.idea.sale.admin.vo.OtherSystemLogEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class LogServiceImpl implements LogService {

	// @Autowired
	// private LogRepository logRepository;
	/**
	 * desc:处理android日志信息，目前不做入库处理 
	 * author:zhangyong 
	 * date:2018年11月30日下午5:35:44 
	 * return:
	 * version 1.0
	 */
	@Override
	public List<AndroidLogEntity> SendAppLogToFlume(HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		List<AndroidLogEntity> list = new ArrayList<>();
		// 获取登陆名称
		String logName = request.getParameter("loginname");
		BufferedReader in = null;
		String inputLine = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				List<Object> json = new ObjectMapper().readValue(inputLine,new TypeReference<List<Object>>(){});
				for (int i = 0; i < json.size(); i++) {
					Object object = json.get(i);
					// 将传过来的参数转化成实体
					AndroidLogEntity androidLogEntity = setAndroidLogEntity(mapper.writeValueAsString(object),mapper);
					// 添加登陆用户和id
					androidLogEntity.setLogName(logName);
					androidLogEntity.setId(UUID.randomUUID().toString());
					// 将实体转化成json字符串
					list.add(androidLogEntity);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	/**
	 * desc:处理ios日志信息，目前不做入库处理
	 * author:zhangyong
	 * date:2018年12月3日下午3:04:49
	 * return:
	 * version 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IOSLogEntity> SendIosAppLogToFlume(HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		BufferedReader in = null;
		String inputLine = null;
		List<IOSLogEntity> result = null;
		try {
			//读取request中的信息
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				//将request中信息转换成json对象
				Map<String, Object> json = mapper.readValue(inputLine, Map.class);
				//处理json数组
				result = handleJson(json,mapper);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	/**
	 * desc:将批量日志处理，转化成JSONArray
	 * author:zhangyong
	 * date:2018年12月3日下午3:05:33
	 * return:JSONArray
	 * version 1.0
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public List<IOSLogEntity> handleJson(Map<String, Object> json,ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		List<IOSLogEntity> list = new ArrayList<IOSLogEntity>();
		//获取公共信息
		String loginName = (String) json.get("LoginName");
		String systemID = (String) json.get("SystemID");
		String clientIP = (String) json.get("ClientIP");
		//将公共信息放入map
		map.put("loginName",loginName);
		map.put("systemID",systemID);
		map.put("clientIP",clientIP);
		//获取地区信息
		Map<String, Object> amap = (Map<String, Object>) json.get("Area");
		String[] array = null;
		try {
			map.putAll(amap);
			//处理界面信息
			List<IOSLogEntity> ControllerArray = (ArrayList<IOSLogEntity>) json.get("ControllerArray");
			
			handleControolerArray(ControllerArray,map,list,mapper);
			//处理事件信息
			List<IOSLogEntity> ActionArray = (ArrayList<IOSLogEntity>)json.get("ActionArray");
			handleActionArray(ActionArray,map,list,mapper);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * desc:处理界面信息
	 * author:zhangyong
	 * date:2018年12月3日下午3:06:27
	 * return:void
	 * version 1.0
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public void handleControolerArray(List controllerArray,Map<String,Object> amap,List<IOSLogEntity> list,ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException{
		Map<String,Object> remap = new HashMap<>();
		remap.putAll(amap);
		//循环数组中的对象
		for(int i=0;i<controllerArray.size();i++) {
			Map<String, Object> object = (Map<String, Object>) controllerArray.get(i);
			for (Map.Entry<String, Object> entry : object.entrySet()) { 
				remap.put(entry.getKey(), entry.getValue());
				}
			IOSLogEntity iosLogEntity = (IOSLogEntity) ObjectUtil.mapToBean(remap,IOSLogEntity.class);
			iosLogEntity.setId(UUID.randomUUID().toString());
			list.add(iosLogEntity);
		}
	}
	/**
	 * desc:处理事件信息
	 * author:zhangyong
	 * date:2018年12月3日下午3:06:50
	 * return:void
	 * version 1.0
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public void handleActionArray(List actionArray,Map<String,Object> amap,List<IOSLogEntity> list,ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException{
		Map<String,Object> remap = new HashMap<>();
		remap.putAll(amap);
		//循环数组中的对象
		for(int i=0;i<actionArray.size();i++) {
			Map<String, Object> object = (Map<String, Object>) actionArray.get(i);
			for (Map.Entry<String, Object> entry : object.entrySet()) { 
				remap.put(entry.getKey(), entry.getValue());
				}
			IOSLogEntity iosLogEntity = (IOSLogEntity) ObjectUtil.mapToBean(remap, IOSLogEntity.class);
			iosLogEntity.setId(UUID.randomUUID().toString());
			list.add(iosLogEntity);
		}
	}
	/**
	 * desc:将andriod app传过来的值设置到实体中 
	 * author:zhangyong 
	 * date:2018年11月28日下午6:03:05
	 * return:void version 1.0
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public AndroidLogEntity setAndroidLogEntity(String obj,ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException {
		// 将传过来的参数转化成map
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.readValue(obj, Map.class);
		// 从map中去除数据，装在成对象
		AndroidLogEntity androidLogEntity = (AndroidLogEntity) ObjectUtil.mapToBean(map, AndroidLogEntity.class);
		androidLogEntity.setId(UUID.randomUUID().toString());
		return androidLogEntity;
	}


	/**
	 * desc: 
	 * author:zhangyong 
	 * date:2018年11月29日下午3:54:56 
	 * return: JSONArray
	 * version 1.0
	 */
	@Override
	public List<OtherSystemLogEntity> SendSystemLogToFlume(HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		List<OtherSystemLogEntity> list = new ArrayList<>();
		// 获取登陆名称
		String logName = request.getParameter("loginname");
		BufferedReader in = null;
		String inputLine = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				List<Object> json = new ObjectMapper().readValue(inputLine,new TypeReference<List<Object>>(){});
				for (int i = 0; i < json.size(); i++) {
					Object object = json.get(i);
					// 将传过来的参数转化成实体
					OtherSystemLogEntity otherLogEntity = setOtherSystemLogEntity(mapper.writeValueAsString(object),mapper);
					// 添加登陆用户和id
					otherLogEntity.setLogName(logName);
					otherLogEntity.setId(UUID.randomUUID().toString());
					// 将实体转化成json字符串
					list.add(otherLogEntity);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	public OtherSystemLogEntity setOtherSystemLogEntity(String obj,ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException {
		// 将传过来的参数转化成map
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.readValue(obj, Map.class);
		// 从map中去除数据，装在成对象
		OtherSystemLogEntity entity = (OtherSystemLogEntity) ObjectUtil.mapToBean(map, OtherSystemLogEntity.class);
		entity.setId(UUID.randomUUID().toString());
		return entity;
	}
}
