package com.kh.web.menu;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MenuOrderServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//메인메뉴, 사이드메뉴, 음료메뉴, 총 결제금액 계산
		String mainMenu = request.getParameter("main_menu");
		String sideMenu = request.getParameter("side_menu");
		String drinkMenu = request.getParameter("drink_menu");
		
		int mainPrice = 0;
		int sidePrice = 0;
		int drinkPrice = 0;
		
		switch(mainMenu) {
			case "한우버거" : mainPrice = 5000; break;
			case "밥버거" : mainPrice = 4500; break;
			case "치즈버거" : mainPrice = 4000; break;
		}
		switch(sideMenu) {
		case "감자튀김" : sidePrice = 1500; break;
		case "어니언링" : sidePrice = 1700; break;
		}
		switch(drinkMenu) {
		case "콜라" : drinkPrice = 1000; break;
		case "사이다" : drinkPrice = 1000; break;
		case "커피" : drinkPrice = 1500; break;
		case "밀크쉐이크" : drinkPrice = 2500; break;
		}
		
		int sum = mainPrice + sidePrice + drinkPrice;
		
		//sum값 request 속성에 담기
		request.setAttribute("sum", sum);
		
		//input값 보내기
		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/menu/menuOrder.jsp");
		reqDispatcher.forward(request, response);
	}
	
	
	

}
