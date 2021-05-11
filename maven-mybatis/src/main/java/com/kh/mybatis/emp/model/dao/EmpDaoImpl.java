package com.kh.mybatis.emp.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class EmpDaoImpl implements EmpDao {

	@Override
	public List<Map<String, Object>> selectAllEmp(SqlSession session) {
		return session.selectList("emp.selectAllEmp");
	}

	@Override
	public List<Map<String, Object>> select1(SqlSession session, Map<String, Object> param) {
		return session.selectList("emp.search1", param);
	}

	@Override
	public List<Map<String, Object>> select2(SqlSession session, Map<String, Object> param) {
		return session.selectList("emp.search2", param);
	}

	@Override
	public List<Map<String, String>> selectJobList(SqlSession session) {
		return session.selectList("emp.selectJobList");
	}

	@Override
	public List<Map<String, Object>> select3(SqlSession session, Map<String, Object> param) {
		return session.selectList("emp.search3", param);
	}

	@Override
	public List<Map<String, String>> selectDeptList(SqlSession session) {
		return session.selectList("emp.selectDeptList");
	}

	@Override
	public Map<String, Object> selectOne(SqlSession session, int empId) {
		return session.selectOne("emp.selectOne", empId);
	}

	@Override
	public int updateEmp(SqlSession session, Map<String, Object> param) {
		System.out.println("쿼리 : "+session.getConfiguration().getMappedStatement("emp.updateEmp").getBoundSql(param).getSql());
		return session.update("emp.updateEmp", param);
	}

}
