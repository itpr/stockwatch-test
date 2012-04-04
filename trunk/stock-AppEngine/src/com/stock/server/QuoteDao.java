package com.stock.server;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;
import com.stock.server.entities.Quote;
import com.stock.server.entities.Stock;
import com.stock.server.entities.UserList;

public class QuoteDao extends DAOBase
{
	
	static{
		ObjectifyService.register(Quote.class);
		ObjectifyService.register(Stock.class);	
	}

	public void save(Quote entity){
		Objectify ofy = ofy();
		ofy.put(entity);
	}
	
	public List<Quote> getAll(){
		Objectify ofy = ofy();
		return ofy.query(Quote.class).list();
	}
	
	public Key<Stock> getStock(Integer id){
		return ofy().query(Stock.class).filter("id", id.longValue()).getKey();
	}

}
