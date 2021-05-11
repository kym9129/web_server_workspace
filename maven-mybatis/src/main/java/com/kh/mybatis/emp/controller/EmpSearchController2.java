package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.emp.model.service.EmpService;
import com.kh.mybatis.emp.model.service.EmpServiceImpl;

public class EmpSearchController2 extends AbstractController {
	private EmpService empService = new EmpServiceImpl();

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1. 사용자 입력값
			String searchType = request.getParameter("searchType");
			String searchKeyword = request.getParameter("searchKeyword");
			String gender = request.getParameter("gender");
			int salary = 0;
			try {
				salary = Integer.parseInt(request.getParameter("salary"));
			}catch(NumberFormatException e) {
				
			}
			String salaryCompare = request.getParameter("salaryCompare");
			String hireDate = request.getParameter("hire_date"); //2021-05-05
			String hireDateCompare = request.getParameter("hireDateCompare");
			
			Date hire_date = null;
			if(hireDate != null && !"".equals(hireDate))
				hire_date = Date.valueOf(hireDate);
			
			Map<String, Object> param = new HashMap<>();
			param.put("searchType", searchType);
			param.put("searchKeyword", searchKeyword);
			param.put("gender", gender);
			param.put("salary", salary);
			param.put("salaryCompare", salaryCompare);
//			param.put("hireDate", hireDate);
			param.put("hire_date", hire_date);
			param.put("hireDateCompare", hireDateCompare);
			System.out.println("param@controller = " + param);
			
			//2. 업무로직
			List<Map<String, Object>> list = empService.select2(param);
			System.out.println("list@controller = " + list);
			
			//3. jsp위임
			request.setAttribute("list", list);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/emp/search2";
	}
}
