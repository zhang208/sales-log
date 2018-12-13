package com.idea.sale.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.idea.sale.admin.vo.AndroidLogEntity;
import com.idea.sale.admin.vo.IOSLogEntity;
import com.idea.sale.admin.vo.OtherSystemLogEntity;

public interface LogService {
	public List<AndroidLogEntity> SendAppLogToFlume(HttpServletRequest request);
	public List<IOSLogEntity> SendIosAppLogToFlume(HttpServletRequest request);
	public List<OtherSystemLogEntity> SendSystemLogToFlume(HttpServletRequest request);
}
