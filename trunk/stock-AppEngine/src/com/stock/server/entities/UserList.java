package com.stock.server.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;


@Entity
public class UserList extends DatastoreObject
{
	private String name;
	private List<Long> stocks;

	public UserList(){
		
	}
	
	public String getName() {
		return name;
	} 

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getStocks() {
		return stocks;
	}

	public void setStocks(List<Long> stocks) {
		this.stocks = stocks;
	}	

}
