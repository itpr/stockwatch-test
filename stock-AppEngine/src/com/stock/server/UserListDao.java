package com.stock.server;

import java.util.List;

import javax.servlet.ServletContext;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;
import com.stock.server.entities.UserList;

public class UserListDao extends DAOBase
{
	
	static{
		ObjectifyService.register(UserList.class);
	}

	public void save(UserList entity){
		Objectify ofy = ofy();
		Key<UserList> k =ofy.put(entity);
		long id = k.getId();
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
		ServletContext context = RequestFactoryServlet.getThreadLocalRequest().getSession().getServletContext();
		SendMessage.sendMessage(context, user.getEmail(), Long.toString(id));
	}
	
	public List<UserList> getAll(){
		Objectify ofy = ofy();
		List<UserList> list = ofy.query(UserList.class).list();
		return list;
	}
	public UserList get(Long id){
		return ofy().query(UserList.class).filter("id", id).get();
	}

}
