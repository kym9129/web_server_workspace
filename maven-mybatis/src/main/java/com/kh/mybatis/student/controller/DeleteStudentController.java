package com.kh.mybatis.student.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.common.exception.NoMatchingStudentException;
import com.kh.mybatis.student.model.service.StudentService;
import com.kh.mybatis.student.model.service.StudentServiceImpl;

public class DeleteStudentController extends AbstractController {
	private StudentService studentService = new StudentServiceImpl();

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			//1. input
			int no = 0;
			try {
				no = Integer.valueOf(request.getParameter("no"));
			}catch(NumberFormatException e) {
				
			}
			//2. business logic
			int result = studentService.deleteStudent(no);
			if(result == 0)
				throw new NoMatchingStudentException(String.valueOf(no));
			System.out.println("result@controller = " + result);
			
			//3. user feedbac & redirect
			request.getSession().setAttribute("msg", "학생 정보 삭제 성공!");
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return "redirect:/student/selectOne.do";
	}
	
	
}
