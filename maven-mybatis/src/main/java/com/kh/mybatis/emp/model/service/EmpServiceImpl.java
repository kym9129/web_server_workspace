package com.kh.mybatis.emp.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import static com.kh.mybatis.common.MybatisUtils.*;

import com.kh.mybatis.emp.model.dao.EmpDao;
import com.kh.mybatis.emp.model.dao.EmpDaoImpl;

public class EmpServiceImpl implements EmpService {
	
	private EmpDao empDao = new EmpDaoImpl();

	@Override
	public List<Map<String, Object>> selectAllEmp() {
		SqlSession session = getSqlSession();
		List<Map<String, Object>> list = empDao.selectAllEmp(session);
		session.close();
		return list;
	}

	@Override
	public List<Map<String, Object>> select1(Map<String, Object> param) {
		SqlSession session = getSqlSession();
		List<Map<String, Object>> list = empDao.select1(session, param);
		session.close();
		return list;
	}

	@Override
	public List<Map<String, Object>> select2(Map<String, Object> param) {
		SqlSession session = getSqlSession();
		List<Map<String, Object>> list = empDao.select2(session, param);
		session.close();
		return list;
	}

	@Override
	public List<Map<String, String>> selectJobList() {
		SqlSession session = getSqlSession();
		List<Map<String, String>> list = empDao.selectJobList(session);
		session.close();
		return list;
	}

	@Override
	public List<Map<String, Object>> select3(Map<String, Object> param) {
		SqlSession session = getSqlSession();
		List<Map<String, Object>> list = empDao.select3(session, param);
		session.close();
		return list;
	}

	@Override
	public List<Map<String, String>> selectDeptList() {
		SqlSession session = getSqlSession();
		List<Map<String, String>> list = empDao.selectDeptList(session);
		session.close();
		return list;
	}

	@Override
	public Map<String, Object> selectOne(int empId) {
		SqlSession session = getSqlSession();
		Map<String, Object> emp = empDao.selectOne(session, empId);
		session.close();
		return emp;
	}

	@Override
	public int updateEmp(Map<String, Object> param) {
		SqlSession session = getSqlSession();
		int result = 0;
		try {
			result = empDao.updateEmp(session, param);
			session.commit();
		}catch(Exception e) {
			session.rollback();
		}finally {
			session.close();
		}
		
		return result;
	}
	
}
