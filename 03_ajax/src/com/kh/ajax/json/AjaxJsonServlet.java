package com.kh.ajax.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class AjaxJsonServlet
 */
@WebServlet("/json")
public class AjaxJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값
		String searchId = request.getParameter("searchId");
		
		//2. 업무로직
		List<Member> list = new ArrayList<>();
		list.add(new Member("hwangj", "황제성", "hwang.jpg"));
		list.add(new Member("dafun", "다프트펑크", "daftpunk.jpg"));
		list.add(new Member("jsyoon", "유재석", "유재석.jpg"));
		
		//검색어가 존재하는 경우
		Member member = null;
		if(searchId != null) {
			for(Member m : list) {
				if(searchId.equals(m.getId())) {
					//검색어가 DB의 Member Id와 일치할 경우 member객체에 담는다
					member = m;
					break; //검색결과 나왔으면 list 순회 종료
				}
			}
		}
		
		//3. JSON문자열로 변환 및 응답메세지에 출력하기
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(searchId != null? member : list);
		//searchId값이 있으면 member를 json으로, 검색어 없으면 list를 json으로 변환
		System.out.println(jsonStr);
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 처리
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		System.out.println("id & name @servlet = " + id + ", " + name);
		
		//2. business logic
		
		//3. response
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().println("회원가입성공!"); //success함수의 data에 이 메세지가 담길 것
	}

}
