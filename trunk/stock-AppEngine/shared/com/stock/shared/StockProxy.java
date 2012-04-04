package com.stock.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.stock.server.entities.Stock", locator = "com.stock.server.ObjectifyLocator")
public interface StockProxy extends EntityProxy 
{
	String getName();
	void setName(String name); 
	Long getId();
	QuoteProxy getQuote();
}


