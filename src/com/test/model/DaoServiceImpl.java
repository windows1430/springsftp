package com.test.model;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DaoServiceImpl extends SqlMapClientDaoSupport implements DaoService{

	public List<Service_tsi_report_fj> getList(Map map) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Service_tsi_report_fjSpace.getService_tsi_report_fjList", map);
	}
}
