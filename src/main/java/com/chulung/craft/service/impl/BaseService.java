package com.chulung.craft.service.impl;

import com.chulung.craft.exception.MethodRuntimeExcetion;
import com.chulung.craft.model.BaseComponent;
import com.chulung.common.util.StringUtil;

public abstract class BaseService extends BaseComponent {

	protected void checkExistBlank(Object... params) {
		for (Object param : params) {
			if (param == null ? true : param instanceof String ? StringUtil.isBlank(param.toString()) : false) {
				logger.error(StringUtil.join(params, ','));
				throw new MethodRuntimeExcetion("必填参数为空");
			}
		}
	}
}