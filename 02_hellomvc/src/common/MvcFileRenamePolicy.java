package common;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MvcFileRenamePolicy implements FileRenamePolicy {

	/**
	 * 벚꽃.jpg -> 20210406090909_123.jpg 파일명 변경
	 */
	@Override
	public File rename(File f) {
		File newFile = null;
		
		//새로운 파일 생성에 실패하면 계속 반복문이 실행된다
		do {
			//새 파일명 생성
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
			DecimalFormat df = new DecimalFormat("000"); 
			//숫자포멧.무조건 3자리수, 1->001 / 11 -> 011
			
			//확장자 구하기
			String oldName = f.getName();
			String ext = ""; //확장자. 빈문자열로 초기화
			int dot = oldName.lastIndexOf("."); //뒤에서부터 찾고 인덱스 리턴
			if(dot > -1) ext = oldName.substring(dot); // ext = ".jpg"
			
			String newName = sdf.format(new Date()) + df.format(Math.random() * 999) + ext;
			
			//파일객체로 변환
			newFile = new File(f.getParent(), newName); 
			//getParent() : 부모경로를 리턴. 부모경로에 새 파일 생성
			
			
		}while(!createNewFile(newFile)); 
		
		
		return newFile;
	}
	
	/**
	 * f가 실제 존재하지 않으면, 파일 생성 후 true 리턴
	 * f가 이미 존재하면, 파일을 생성하지 않고, IOException을 던짐.
	 */
	  private boolean createNewFile(File f) {
		    try {
		      return f.createNewFile();
		    }
		    catch (IOException ignored) {
		      return false;
		    }
		  }

}
