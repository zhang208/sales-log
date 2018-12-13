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
public class AndroidLogEntity {
	/**
	 * id主键
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	/**
	 * 模块名
	 * 按销售助手APP模块划分，在模块入口进行埋点
	 */
	private String modular;
	/**
	 * 模块访问量
	 *//*
	private long modular_num;
	*//**
	 * 模块访问时长
	 *//*
	private long modular_duration;
	*//**
	 * 模块访问时间段
	 *//*
	private String modular_timeFrame;
	*//**
	 * 模块访问地区
	 *//*
	private String modular_address;*/
	/**
	 * 详细内容
	 * 各模块中详细页面的页面名，在父类组件中统一埋点统计
	 */
	private String modNam;
	/**
	 * 详细内容访问量
	 *//*
	private long modNam_num;
	*//**
	 * 详细内容访问时长
	 *//*
	private long modNam_duration;
	*//**
	 * 详细内容访问时间段
	 *//*
	private String modNam_timeFrame;
	*//**
	 * 详细内容访问地区
	 *//*
	private String modNam_address;*/
	/**
	 * 开始时间
	 * 进入详细页面的时间（时间戳，精确到秒）
	 */
	private Date startTime;
	/**
	 * 结束时间
	 * 关闭详细页面的时间（时间戳，精确到秒）
	 */
	private Date endTime;
	/**
	 * 访问地区
	 * 	访问详细页面时所在区域
	 */
	private String place;
	/**
	 * 日志类型 （window/view）
	 */
	private String type;
	/**
	 * 登陆用户id
	 */
	private String logName;
}
