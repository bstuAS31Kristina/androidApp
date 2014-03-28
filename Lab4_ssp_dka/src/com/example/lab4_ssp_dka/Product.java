package com.example.lab4_ssp_dka;

import java.io.Serializable;

public class Product implements Serializable {

	private long id;
	private static final long serialVersionUID = 1L;
	private String name;
	private double price;
	private int count;
	private long idDeal;
	
	public Product() {
		setId(0);
		name = new String();
		price = 0;
		count = 0;
	}
	
	public Product(String name, double price, int count){
		this.setName(name);
		this.setPrice(price);
		this.setCount(count);
	}
	
	
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}	
	@Override
	public String toString() {
		return String.format("Name: %s\nPrice: %.1f\nCount: %d", this.getName(), this.getPrice(), this.getCount());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdDeal() {
		return idDeal;
	}

	public void setIdDeal(long idDeal) {
		this.idDeal = idDeal;
	}
}
