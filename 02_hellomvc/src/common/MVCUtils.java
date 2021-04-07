package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MvcUtils {

	/**
	 * 
	 * 단방향 암호화 알고리즘
	 */
	public static String getsha512(String password) {
		String encryptedPassword = null;
		
		//1. 암호화
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA-512");	
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] bytes = null;
		try {
			bytes = password.getBytes("UTF-8"); //문자열을 byte[]로 변환해주는 String 메소드
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		md.update(bytes);
		byte[] encryptedBytes = md.digest(); //암호화 처리
		
		System.out.println("암호화 처리 후 : " + new String(encryptedBytes));
		
		//2. 문자 인코딩 처리
		encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
		System.out.println("인코딩 처리 후 : " + new String(encryptedPassword));
		
		return encryptedPassword;
	}

	/**
	 * ## Paging Recipe

	파라미터
	1. cPage(현재페이지), 
	2. numPerPage(페이지 당 표시할 컨텐츠 수)
	3. totalContents(총 컨텐츠 수)
	4. url 이동할 주소 /mvc/admin/memberList?cPage=
	-----------------------------
	5. totalPage(전체 페이지 수) - pageNo 넘침 방지
	6. pageBarSize (페이지바에 표시할 페이지 개수 지정 : 5) (이전 1 2 3 4 5  다음 ...10)
	7. pageStart ~ pageEnd (pageNo의 범위)
	8. pageNo (페이지 넘버를 출력할 증감변수)
	 */
	public static String getPageBar(int cPage, int numPerPage, int totalContents, String url) {
		//계속 추가될 값이라 뮤터블한 stringBuilder사용
		StringBuilder pageBar = new StringBuilder();
		int totalPage = (int)Math.ceil((double)totalContents / numPerPage);
		int pageBarSize = 5;
		//cPage속성 추가 전 키워드 작업
		//cPage이외의 다른 사용자 입력값이 있는 경우(?가 이미 있는 경우) 대비
		//  /mvc/admin/memberFinder?type=id&kw=abc
		url = url.indexOf("?") > -1 ? url + "&" : url + "?";
		
		/**
		 * pageStart 
		 * 1 2 3 4 5   ----> 1
		 * 6 7 8 9 10  -----> 6
		 * 11 12 13 14 15  -----> 11
		 * ...
		 * 
		 * 공식. 이해안돼도 패스!
		 */
		int pageStart = (cPage - 1) / pageBarSize * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;
		
		//증감변수는 pageStart부터 시작
		int pageNo = pageStart;
		
		/**
		 * 이전 1 2 3 4 5 다음
		 * 이전 6 7 8 9 10 다음
		 * 이전 11 
		 */
		
		//1. 이전
		if(pageNo != 1 ) {
//			pageBar.append("<a href='"+url+"?cPage="+(pageNo-1)+"'/></a>");
			pageBar.append("<a href='"+url+"cPage="+(pageNo-1)+"'/>prev</a>\n");
		}
		
		//2. pageNo
		while(pageNo <= pageEnd && pageNo <= totalPage) {
			if(pageNo == cPage) {
				//pageNo이 현재페이지이면 링크를 없에려고 span태그로 작성
				pageBar.append("<span class='cPage'>" + pageNo + "</span>");
			}else {
				pageBar.append("<a href='"+ url +"cPage=" + pageNo + "'>" + pageNo + "</a>\n");
			}
			pageNo++;
		}
		
		//3. 다음
		// 마지막 페이지가 포함된 페이지바인 경우
		if(pageNo > totalPage) {
			
		}else {
			pageBar.append("<a href='"+ url +"cPage=" + (pageNo) + "'>next</a>\n");
		}
		/**
		 <div id="pageBar">
			<a href='/mvc/admin/memberlist?cPage=5'>prev</a>
			<span class='cPage'>6</span><a href='/mvc/admin/memberlist?cPage=6'>7</a>
			<a href='/mvc/admin/memberlist?cPage=7'>8</a>
			<a href='/mvc/admin/memberlist?cPage=8'>9</a>
			<a href='/mvc/admin/memberlist?cPage=9'>10</a>
			<a href='/mvc/admin/memberlist?cPage=11'>next</a>
		</div>
		 */

		
		return pageBar.toString();
	}

	public static String convertLineFeedToBr(String content) {
		return content.replaceAll("\\n", "<br/>");
	}

	public static String escapeHtml(String str) {
		return str.replaceAll("<", "&lt")
				.replaceAll(">", "&gt;");
	}

}
