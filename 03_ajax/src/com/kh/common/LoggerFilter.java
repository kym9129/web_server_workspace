package com.kh.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoggerFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
//		System.out.println("LoggerFilter의 init메소드 호출!");
	}

	@Override
	public void destroy() {
//		System.out.println("LoggerFilter의 destroy메소드 호출!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//1. servlet 호출 전
		System.out.println("\n=============================");
		HttpServletRequest httpReq = (HttpServletRequest)request;
		String uri = httpReq.getRequestURI();
		System.out.println(uri);
		System.out.println("-----------------------------");
		
		//다음 filterChain객체를 호출
		//다음 filter객체가 존재하지 않으면, servlet을 호출한다
		chain.doFilter(request, response);
		
		//2. servlet/jsp 처리 이후
		System.out.println("_______________________________");
		
	}

}
