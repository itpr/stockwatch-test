package com.stock.server;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
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
		for(Stock s : list){
			s.setQuote(getChildren(s.getId()));
		}
		return list;
	}
	
    public List<Quote> getChildren(Stock ss) {  
        Objectify ofy = ofy(); 
        Query<Quote> children = ofy.query(Quote.class).ancestor(ss);  
        return children.list();  
    }  
    
    public List<Quote> getChildrens(long id){
    	Objectify ofy = ofy();
    	List<Quote> qt = ofy.query(Quote.class).filter("symbol", id).order("-date").limit(10).list();
    	return qt;
    }
    
    public Quote getChildren(long id){
    	Objectify ofy = ofy();
    	Quote qt = ofy.query(Quote.class).filter("symbol", id).order("-date").get();
    	return qt;
    }


}
