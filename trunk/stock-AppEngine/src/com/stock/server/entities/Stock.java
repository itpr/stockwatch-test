package com.stock.server.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.annotation.Entity;


@Entity
public class Stock extends DatastoreObject
{
	private String name;	
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
	

}
