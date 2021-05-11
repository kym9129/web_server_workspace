package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.common.exception.NoMatchingEmpException;
import com.kh.mybatis.emp.model.service.EmpService;
import com.kh.mybatis.emp.model.service.EmpServiceImpl;

public class UpdateEmpController extends AbstractController {

	private EmpService empService = new EmpServiceImpl();

	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. 사용자 입력값
			int empId = 0;
			try {
				empId = Integer.parseInt(request.getParameter("empId"));
			}catch(NumberFormatException e) {
				
			}
			
			//2. 업무로직 : 직급목록, 부서목록, 사원1명 정보(부서명, 직급명)
			List<Map<String, String>> jobList = empService.selectJobList();
			List<Map<String, String>> deptList = empService.selectDeptList();
			Map<String, Object> emp = empService.selectOne(empId);
			System.out.println("emp@controller = " + emp);
			
			//3. jsp위임
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			request.setAttribute("emp", emp);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return "/emp/updateEmp";
	}
	
	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int empId = 0;
		try {
			//1. user input
			try {
				empId = Integer.parseInt(request.getParameter("empId"));
			}catch(NumberFormatException e) {
				
			}
			String jobCode = request.getParameter("jobCode");
			String deptId = request.getParameter("deptId");
			
			Map<String, Object> param = new HashMap<>();
			param.put("empId", empId);
			param.put("jobCode", jobCode);
			param.put("deptId", deptId);
			System.out.println("param@controller = " + param);
			
			//2. business logic. update
			int result = empService.updateEmp(param);
			if(result == 0)
				throw new NoMatchingEmpException(String.valueOf(empId));
			//3. user feedback & redirect
			request.getSession().setAttribute("msg", "사번 " +empId+ "번 사원 정보 수정 성공!");
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/emp/search3.do";
	}
	
	
	
}
