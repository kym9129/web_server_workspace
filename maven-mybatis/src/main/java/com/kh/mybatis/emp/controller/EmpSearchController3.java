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

public class EmpSearchController3 extends AbstractController {
	private EmpService empService = new EmpServiceImpl();

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1. 사용자 입력값
			String[] jobCodeArr = request.getParameterValues("jobcode");
			String[] deptCodeArr = request.getParameterValues("deptcode");
			
			Map<String, Object> param = new HashMap<>();
			param.put("jobCodeArr", jobCodeArr);
			param.put("deptCodeArr", deptCodeArr);
			
			System.out.println("param@controller = " + param);
			
			//2. 업무로직
			//jobList(job_code, job_name)
			List<Map<String, String>> jobList = empService.selectJobList();
			System.out.println("jobList@controller = " + jobList);
			
			//deptList(dept_id, dept_title)
			List<Map<String, String>> deptList = empService.selectDeptList();
			System.out.println("deptList@controller = " + deptList);
			
			 
			List<Map<String, Object>> list = empService.select3(param);
			System.out.println("list@controller = " + list);
			
			//3. jsp위임
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			request.setAttribute("list", list);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/emp/search3";
	}
}
