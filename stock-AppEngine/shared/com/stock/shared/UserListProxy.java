package com.stock.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.stock.server.entities.UserList", locator = "com.stock.server.ObjectifyLocator")
public interface UserListProxy extends EntityProxy 
{
	String getName();
	void setName(String name);
	List<Long> getStocks();
	void setStocks(List<Long> stocks);
}


