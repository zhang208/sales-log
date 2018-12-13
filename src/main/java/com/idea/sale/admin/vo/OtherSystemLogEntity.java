package com.idea.sale.admin.vo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**  
* Title: OtherSystemLogEntity
* Description: 
* @author zhangyong
* @date 2018年11月29日下午2:07:59  
*/
@Data
@Entity
public class OtherSystemLogEntity {
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	/**
	 * 系统id
	 */
	private String sysId;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 调用时间
	 */
	private Date callDate;
	/**
	 * 响应时间
	 */
	private Date respondTime;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 请求方法（包含包类）
	 */
	private String requestMethodName;
	private String logName;
}
