package com.kh.mybatis.emp.model.service;

import java.util.List;
import java.util.Map;

public interface EmpService {

	public List<Map<String, Object>> selectAllEmp();

	public List<Map<String, Object>> select1(Map<String, Object> param);

	public List<Map<String, Object>> select2(Map<String, Object> param);

	public List<Map<String, String>> selectJobList();

	public List<Map<String, Object>> select3(Map<String, Object> param);

	public List<Map<String, String>> selectDeptList();

	public Map<String, Object> selectOne(int empId);

	public int updateEmp(Map<String, Object> param);

}
 