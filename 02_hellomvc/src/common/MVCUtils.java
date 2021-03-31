package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MVCUtils {

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

}
