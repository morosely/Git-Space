package com.flyweight;

public class Main {

	public static void main(String[] args) {
		ReportManagerFactory factory = new ReportManagerFactory();
		IReportManager r = factory.getFinancialReportManager("A");
		System.out.println(r.createReport());
	}

}
