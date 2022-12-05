package com.flyweight;

public class FinancialReportManager implements IReportManager {

	private String tenantId;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public FinancialReportManager(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String createReport() {
		return "This is financial report";
	}

}
