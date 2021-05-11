package com.kh.mybatis.emp.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public interface EmpDao {

	List<Map<String, Object>> selectAllEmp(SqlSession session);

	List<Map<String, Object>> select1(SqlSession session, Map<String, Object> param);

	List<Map<String, Object>> select2(SqlSession session, Map<String, Object> param);

	List<Map<String, String>> selectJobList(SqlSession session);

	List<Map<String, Object>> select3(SqlSession session, Map<String, Object> param);

	List<Map<String, String>> selectDeptList(SqlSession session);

	Map<String, Object> selectOne(SqlSession session, int empId);

	int updateEmp(SqlSession session, Map<String, Object> param);

}
