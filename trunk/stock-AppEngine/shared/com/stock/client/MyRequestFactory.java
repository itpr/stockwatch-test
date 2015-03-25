
package com.stock.client;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.ServiceName;
import com.stock.shared.MessageProxy;
import com.stock.shared.QuoteProxy;
import com.stock.shared.RegistrationInfoProxy;
import com.stock.shared.StockProxy;
import com.stock.shared.UserListProxy;
import com.stock.shared.charts.TimePeriod;


public interface MyRequestFactory extends RequestFactory {

  @ServiceName("com.stock.server.HelloWorldService")
  public interface HelloWorldRequest extends RequestContext {
    
    Request<String> getMessage();
  }

  @ServiceName("com.stock.server.RegistrationInfo")
  public interface RegistrationInfoRequest extends RequestContext {
    
    InstanceRequest<RegistrationInfoProxy, Void> register();

    
    InstanceRequest<RegistrationInfoProxy, Void> unregister();
  }

  @ServiceName("com.stock.server.Message")
  public interface MessageRequest extends RequestContext {
    
    InstanceRequest<MessageProxy, String> send();
  }
  
  @ServiceName(value = "com.stock.server.StockDao", locator = "com.stock.server.DaoServiceLocator")
  public interface StockRequest extends RequestContext {
    Request<Void> save(StockProxy entity);
    Request<List<StockProxy>> getAll();
    Request<List<StockProxy>> getAllWithChildren();
  }
  

  
  @ServiceName(value = "com.stock.server.UserListDao", locator = "com.stock.server.DaoServiceLocator")
  public interface UserListRequest extends RequestContext {
    Request<Void> save(UserListProxy entity);
    Request<List<UserListProxy>> getAll();
    Request<UserListProxy> get(Long id);
  }
  
  
  @ServiceName(value = "com.stock.server.QuoteDao", locator = "com.stock.server.DaoServiceLocator")
  public interface QuoteRequest extends RequestContext {
    Request<Void> save(QuoteProxy entity);
    Request<List<QuoteProxy>> getAllById(Long id, TimePeriod timePeriod);
  }

  HelloWorldRequest helloWorldRequest();

  RegistrationInfoRequest registrationInfoRequest();

  MessageRequest messageRequest();
  
  StockRequest stockRequest();
  
  UserListRequest userListRequest();
  
  QuoteRequest quoteRequest();
}
