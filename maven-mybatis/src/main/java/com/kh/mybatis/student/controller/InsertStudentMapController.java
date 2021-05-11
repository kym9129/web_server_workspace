package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.StudentService;
import com.kh.mybatis.student.model.service.StudentServiceImpl;

public class InsertStudentMapController extends AbstractController {

	private StudentService studentService = new StudentServiceImpl();

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1. 사용자 입력값
			String name = request.getParameter("name");
			String tel = request.getParameter("tel");
			
			//지금까진 VO클래스에 담았지만 이번엔 Map에 담아서 처리
			// -> 이렇게 처리하면 VO클래스를 만들 필요가 없어진다.
			Map<String, Object> student = new HashMap<>();
			student.put("name", name);
			student.put("tel", tel);
			System.out.println("studentMap@controller = " + student);
			
			//2. 업무로직
			int result = studentService.insertStudentMap(student);
			
			
			//3. 사용자 피드백 & redirect
			request.getSession().setAttribute("msg", "학생 정보 등록 성공!");
		} catch (Exception e) {
			e.printStackTrace();
			throw e; //container에 에러 던지기
		}
		
		return "redirect:/student/insertStudent.do";
	}
	
	
	
}
