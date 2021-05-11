package com.kh.mybatis.student.model.service;

import static com.kh.mybatis.common.MybatisUtils.getSqlSession;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.common.MybatisUtils;
import com.kh.mybatis.student.model.dao.StudentDao;
import com.kh.mybatis.student.model.dao.StudentDaoImpl;
import com.kh.mybatis.student.model.vo.Student;

public class StudentServiceImpl implements StudentService {
	
	private StudentDao studentDao = new StudentDaoImpl();

	@Override
	public int insertStudent(Student student) {
		SqlSession session = MybatisUtils.getSqlSession();
		int result = 0;
		try {
			//1. SqlSession 생성 (Connection생성 대신. mybatis의 역할)
			//2. dao 업무위임
			result = studentDao.insertStudent(session, student);
			//3. transaction처리: SqlSession에 대해서 commit | rollback
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw e; //controller에게 던지기
		} finally {
			//4. SqlSession 자원반납
			session.close();
		}
		
		return result;
	}

	@Override
	public int insertStudentMap(Map<String, Object> student) {
		int result = 0;
		SqlSession session = getSqlSession();
		try {
			//DML은 트랜젝션 처리
			result = studentDao.insertStudentMap(session, student);
			//insert는 무조건 result가 1임. insert가 안되면 무조건 오류가 남
			session.commit();
		} catch (Exception e) {
			session.rollback(); //autocommit false한 이유가 이거 하려고
			throw e; // controller에 던지기
		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public int selectStudentCount() {
		SqlSession session = getSqlSession();
		int total = studentDao.selectStudentCount(session);
		session.close(); //dql은 트랜젝션 처리 없이 바로 자원반납
		return total;
	}

	@Override
	public Student selectOneStudent(int no) {
		SqlSession session = getSqlSession();
		Student student = studentDao.selectOneStudent(session, no);
		session.close();
		return student;
	}

	@Override
	public Map<String, Object> selectOneStudentMap(int no) {
		SqlSession session = getSqlSession();
		Map<String, Object> student = studentDao.selectOneStudentMap(session, no);
		session.close();
		return student;
	}

	@Override
	public int updateStudent(Student student) {
		SqlSession session = getSqlSession();
		int result = 0;
		try {
			result = studentDao.updateStudent(session, student);
			session.commit();
			
		}catch(Exception e) {
			session.rollback();
			throw e;
		}finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public int deleteStudent(int no) {
		SqlSession session = getSqlSession();
		int result = 0;
		try {
			result = studentDao.deleteStudent(session, no);
			session.commit();
			
		}catch(Exception e) {
			session.rollback();
			throw e;
		}finally {
			session.close();
		}
		return result;
	}

	@Override
	public List<Student> selectStudentList() {
		SqlSession session = getSqlSession();
		List<Student> list = studentDao.selectStudentList(session);
		session.close();
		return list;
	}

	@Override
	public List<Map<String, Object>> selectStudentMapList() {
		SqlSession session = getSqlSession();
		List<Map<String, Object>> mapList = studentDao.selectStudentMapList(session);
		session.close();
		return mapList;
	}

	
	
	

}
