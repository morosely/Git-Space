package com.flyweight;

public class EmployeeReportManager implements IReportManager {

	private String tenantId;
	
	public EmployeeReportManager(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String createReport() {
		return "This is employee report";
	}

}
