package com.stock.server.entities;

import com.googlecode.objectify.annotation.Entity;


@Entity
public class Stock extends DatastoreObject
{	
	private String name;
	private String shortName;
	private Quote quote;
	
	
	public Stock(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	

}
