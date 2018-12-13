package com.idea.sale.admin.repository;

import org.springframework.data.jpa.repository.Query;

import com.idea.sale.admin.vo.AndroidLogEntity;
import com.idea.sale.admin.vo.IOSLogEntity;

/**  
* Title: LogRepository
* Description: 暂时不做入库处理
* @author zhangyong
* @date 2018年11月28日下午4:33:42  
*/

public interface LogRepository {
	@Query(name="")
	public void addAndrLog(AndroidLogEntity androidLogEntity);
	@Query(name="")
	public void addIosLog(IOSLogEntity iosLogEntity); 
}
