package com.chulung.craft.interceptor;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chulung.craft.tracker.Tracker;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chulung.craft.constant.Constants;

/**
 * 全局tracker 拦截器 拦截所有请求，包括搜索引擎
 * 
 * @author ChuKai
 *
 */
public class GlobalTrackerInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private Tracker tracker;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		validateSessionId((HttpServletRequest) request, (HttpServletResponse) response);
		tracker.track(request);
		return super.preHandle(request, response, handler);
	}

	private void validateSessionId(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constants.TUID.equals(cookie.getName())) {
					return;
				}
			}
		}
		Cookie cookie = new Cookie(Constants.TUID, UUID.randomUUID().toString());
		cookie.setDomain("chulung.com");
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
	}
}
