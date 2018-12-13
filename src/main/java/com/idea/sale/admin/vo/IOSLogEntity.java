package com.idea.sale.admin.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="")
public class IOSLogEntity {
	/**
	 * id主键
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	/**
	 * ip信息：
	 *  每次进入应用时统计ip地址，直到使用结束，每次从新打开app时刷新，信息保存到每一条数据中
	 */
	private String clientIP;
	private String loginName;

	private String systemID;
	/**
	 *  界面信息_模块名称
	 */
	private String modularName;
	/**
	 *    界面信息_类名
	 */
	private String className;
	/**
	 * 界面信息_进入时间
	 */
	private Date beginTime;
	/**
	 * 界面信息_结束时间
	 */
	private Date endTime;
	/**
	 * 类型(按钮[action]/界面[controller])
	 */
	private String type;
	/**
	 * 点击时间
	 */
	private Date time;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 方法名
	 */
	private String methodName;
	/**
	 * 页面唯一标识
	 */
	private int tag;
	
	
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 维度
	 */
	private String latitude;
	/**
	 * 市
	 */
	private String locality;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 匀
	 */
	private String subLocality;
	/**
	 * 街道
	 */
	private String thoroughfare;
	/**
	 * 具体位置
	 */
	private String name;
	/**
	 * 省
	 */
	private String administrativeArea;
	
	
	
}
