package com.example.lab4_ssp_dka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Deal implements Serializable{
//	private static long counter = 1;
//	static long generateID() {
//		return counter++;
//	}
//	
//	final long id = generateID();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private long sellerId;
	private long buyerId;
	private List<Product> product;
	private String date;
	
	public Deal() {
		name = new String();
		this.setBuyerId(0);
		this.setSellerId(0);
		product = new ArrayList<Product>();
		date = new String();
	}
	
	public Deal(String name, int sellerId, int buyerId, String date) {
		this.setName(name);
		this.setBuyerId(buyerId);
		this.setSellerId(sellerId);
		product = new ArrayList<Product>();
		this.setDate(date);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product.add(product);
	}
	
	public void setProductList(List<Product> product) {
		this.product = product;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
		public String toString() {
			StringBuilder str = new StringBuilder();
			str.append(String.format("Name: %s\nDate: %s\n\nPRODUCTS:\n", this.getName(), this.getDate()));
			for (Product prod : this.getProduct()) {
				str.append(prod.toString());
				str.append("\n");
			}
			return str.toString();
		}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
