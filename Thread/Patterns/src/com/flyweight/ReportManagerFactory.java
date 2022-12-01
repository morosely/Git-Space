package com.flyweight;

import java.util.HashMap;
import java.util.Map;

public class ReportManagerFactory {
	
	Map<String,IReportManager> financialReportManagerMap = new HashMap<String,IReportManager>();
	Map<String,IReportManager> employeeReportManagerMap = new HashMap<String,IReportManager>();
	
	IReportManager getFinancialReportManager(String tenantId){
		IReportManager r = financialReportManagerMap.get(tenantId);
		if(r == null){
			r = new FinancialReportManager(tenantId);
			financialReportManagerMap.put(tenantId, r);
		}
		return r;
	}
	
	IReportManager getEmployeeReportManager(String tenantId){
		IReportManager r = employeeReportManagerMap.get(tenantId);
		if(r == null){
			r = new FinancialReportManager(tenantId);
			employeeReportManagerMap.put(tenantId, r);
		}
		return r;
	}
}
