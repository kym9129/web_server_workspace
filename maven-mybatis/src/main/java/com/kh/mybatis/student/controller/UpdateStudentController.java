package com.kh.mybatis.student.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.common.exception.NoMatchingStudentException;
import com.kh.mybatis.student.model.service.StudentService;
import com.kh.mybatis.student.model.service.StudentServiceImpl;
import com.kh.mybatis.student.model.vo.Student;

public class UpdateStudentController extends AbstractController {

	StudentService studentService = new StudentServiceImpl();

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int no = 0;
		try {
			//1. user input
			try {
				no = Integer.valueOf(request.getParameter("no"));
			} catch(NumberFormatException e) {
				//처리할 코드 없음
			}
			String name = request.getParameter("name");
			String tel = request.getParameter("tel");
			Student student = new Student();
			student.setNo(no);
			student.setName(name);
			student.setTel(tel);
			
			//2. business logic
			int result = studentService.updateStudent(student);
			if(result == 0)
				throw new NoMatchingStudentException(String.valueOf(no));
			
			//3. user feedback & redirect
			request.getSession().setAttribute("msg", "학생 정보 수정 성공!");
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/student/selectOne.do?no=" + no;
	}
	
	
}
