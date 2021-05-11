package com.kh.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class AjaxXmlServlet
 */
@WebServlet("/xml")
public class AjaxXmlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 업무로직
		List<Member> list = new ArrayList<>();
		list.add(new Member("hwangj", "황제성", "hwang.jpg"));
		list.add(new Member("dafun", "다프트펑크", "daftpunk.jpg"));
		list.add(new Member("jsyoon", "유재석", "유재석.jpg"));
		
		//2. jsp위임
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/xml/members.jsp").forward(request, response);
		
	}

}
