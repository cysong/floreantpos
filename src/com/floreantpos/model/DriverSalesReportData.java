package com.floreantpos.model;
public class DriverSalesReportData{
	private String driverId;
	private String driverName;
	private double totalSales;
	private double totalGratuity;
	private double totalOrder;
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	public double getTotalGratuity() {
		return totalGratuity;
	}
	public void setTotalGratuity(double totalGratuity) {
		this.totalGratuity = totalGratuity;
	}
	public double getTotalOrder() {
		return totalOrder;
	}
	public void setTotalOrder(double totalOrder) {
		this.totalOrder = totalOrder;
	}
}