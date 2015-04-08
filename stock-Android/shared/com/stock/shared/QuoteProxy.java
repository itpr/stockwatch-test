package com.stock.shared;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.stock.server.entities.Quote", locator = "com.stock.server.ObjectifyLocator")
public interface QuoteProxy extends EntityProxy 
{
	Long getSymbol();
	void setSymbol(Long name); 
	public Date getDate();

	public void setDate(Date date);

	public Double getOpen();

	public void setOpen(Double open);

	public Double getHigh();

	public void setHigh(Double high);

	public Double getLow();

	public void setLow(Double low);

	public Double getClose();

	public void setClose(Double close);

	public Integer getVolume();

	public void setVolume(Integer volume);
}


