package com.kgl.KglServices.pojo;

public class CallObj {

    String tid;
	String userph;
	String custph;
	String table;
	String exotel;
	public String getTid() {
		return tid;
	}
	public String getUserph() {
		return userph;
	}
	public String getCustph() {
		return custph;
	}
	public String getTable() {
		return table;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public void setUserph(String userph) {
		this.userph = userph;
	}
	public void setCustph(String custph) {
		this.custph = custph;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	public String getExotel() {
		return exotel;
	}
	public void setExotel(String exotel) {
		this.exotel = exotel;
	}
	@Override
	public String toString() {
		return "CallObj [tid=" + tid + ", userph=" + userph + ", custph=" + custph + ", table=" + table + ", exotel="
				+ exotel + "]";
	}
	
}
