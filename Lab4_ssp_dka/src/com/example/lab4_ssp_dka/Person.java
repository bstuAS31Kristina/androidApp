package com.example.lab4_ssp_dka;

import java.io.Serializable;

public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String name;
	private String company;
	
	public Person(){
		this.name = new String();
		this.company = new String();
	}
	
	public Person(String name, String company) {
		this.setName(name);
		this.setCompany(company);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return String.format("Name: %s\nCompany: %s", this.getName(), this.getCompany());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
//	public Date getbDay() {
//		return bDay;
//	}
//
//	public void setbDay(Date bDay) {
//		this.bDay = bDay;
//	}
//
//	public String getSex() {
//		return sex;
//	}
//
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
}
