package com.stock.server;

import java.util.List;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;
import com.stock.server.entities.Quote;
import com.stock.server.entities.Stock;

public class StockDao extends DAOBase
{
	
	static{
		ObjectifyService.register(Stock.class);
		ObjectifyService.register(Quote.class);
	}

	public void save(Stock entity){
		Objectify ofy = ofy();
		ofy.put(entity);
	}
	
	public List<Stock> getAll(){
		Objectify ofy = ofy();
		return ofy.query(Stock.class).list();
	}
	
	public List<Stock> getAllWithChildren(){
		Objectify ofy = ofy();
		List<Stock> list = ofy.query(Stock.class).list();
		QuoteDao quoteDao = new QuoteDao();
		for(Stock s : list){
			s.setQuote(quoteDao.getLatestQuote(s.getId()));
		}
		return list;
	}

}
