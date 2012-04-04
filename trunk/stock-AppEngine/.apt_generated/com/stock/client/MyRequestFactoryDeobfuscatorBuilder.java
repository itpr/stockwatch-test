// Automatically Generated -- DO NOT EDIT
// com.stock.client.MyRequestFactory
package com.stock.client;
import java.util.Arrays;
import com.google.web.bindery.requestfactory.vm.impl.OperationData;
import com.google.web.bindery.requestfactory.vm.impl.OperationKey;
public final class MyRequestFactoryDeobfuscatorBuilder extends com.google.web.bindery.requestfactory.vm.impl.Deobfuscator.Builder {
{
withOperation(new OperationKey("erc8e9i16goUfpY4VPEaZGHUYKs="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("send")
  .withRequestContext("com.stock.client.MyRequestFactory$MessageRequest")
  .build());
withOperation(new OperationKey("oz2iOEYhMUfXPLwH3cExYB7MAYU="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("getMessage")
  .withRequestContext("com.stock.client.MyRequestFactory$HelloWorldRequest")
  .build());
withOperation(new OperationKey("z$NH2YDWwTtOPfruBEd8NYLT9Co="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("register")
  .withRequestContext("com.stock.client.MyRequestFactory$RegistrationInfoRequest")
  .build());
withOperation(new OperationKey("yA2BYFu51jzdpFUJpoDXcXgl_wE="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("unregister")
  .withRequestContext("com.stock.client.MyRequestFactory$RegistrationInfoRequest")
  .build());
withOperation(new OperationKey("30rldlr_p6dBvoBRsQ6Nd$flH94="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Lcom/stock/shared/StockProxy;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Lcom/stock/server/entities/Stock;)V")
  .withMethodName("save")
  .withRequestContext("com.stock.client.MyRequestFactory$StockRequest")
  .build());
withOperation(new OperationKey("PsNZb7uQH2jl2m3sKDhp6kNBI08="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(J)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(J)Lcom/stock/server/entities/Quote;")
  .withMethodName("getChildren")
  .withRequestContext("com.stock.client.MyRequestFactory$StockRequest")
  .build());
withOperation(new OperationKey("izTIoFwtKfJZL5OdEGwFOfc7Pow="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Lcom/stock/shared/StockProxy;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Lcom/stock/server/entities/Stock;)Ljava/util/List;")
  .withMethodName("getChildren")
  .withRequestContext("com.stock.client.MyRequestFactory$StockRequest")
  .build());
withOperation(new OperationKey("0ejk33nvo8m_ya1IhnsWfJXIzzA="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/util/List;")
  .withMethodName("getAll")
  .withRequestContext("com.stock.client.MyRequestFactory$StockRequest")
  .build());
withOperation(new OperationKey("2_FJKEoprKEFo7B7bympzQ7qGMU="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/util/List;")
  .withMethodName("getAllWithChildren")
  .withRequestContext("com.stock.client.MyRequestFactory$StockRequest")
  .build());
withOperation(new OperationKey("HBPxqMw6mlkUaJ43dv4W8ru2PRY="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Lcom/stock/shared/QuoteProxy;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Lcom/stock/server/entities/Quote;)V")
  .withMethodName("save")
  .withRequestContext("com.stock.client.MyRequestFactory$QuoteRequest")
  .build());
withOperation(new OperationKey("MZaH0YcfJO2HUEEn5rlfzfe4yIU="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/util/List;")
  .withMethodName("getAll")
  .withRequestContext("com.stock.client.MyRequestFactory$QuoteRequest")
  .build());
withOperation(new OperationKey("MNCLqaPEK3uQMiBuC8er2H_6cPQ="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Ljava/lang/Long;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Ljava/lang/Long;)Lcom/stock/server/entities/UserList;")
  .withMethodName("get")
  .withRequestContext("com.stock.client.MyRequestFactory$UserListRequest")
  .build());
withOperation(new OperationKey("VZATnOy$jWKgdkr4oAvOA5ecjKk="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Lcom/stock/shared/UserListProxy;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Lcom/stock/server/entities/UserList;)V")
  .withMethodName("save")
  .withRequestContext("com.stock.client.MyRequestFactory$UserListRequest")
  .build());
withOperation(new OperationKey("URadVJ6wAcgaTEsWCtoE_HjtNig="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/util/List;")
  .withMethodName("getAll")
  .withRequestContext("com.stock.client.MyRequestFactory$UserListRequest")
  .build());
withRawTypeToken("w1Qg$YHpDaNcHrR5HZ$23y518nA=", "com.google.web.bindery.requestfactory.shared.EntityProxy");
withRawTypeToken("8KVVbwaaAtl6KgQNlOTsLCp9TIU=", "com.google.web.bindery.requestfactory.shared.ValueProxy");
withRawTypeToken("FXHD5YU0TiUl3uBaepdkYaowx9k=", "com.google.web.bindery.requestfactory.shared.BaseProxy");
withRawTypeToken("QGJ2ltTrzlZyrZp72Izn9p_NJTQ=", "com.stock.shared.MessageProxy");
withRawTypeToken("y1jFzP37Y7aqB3HtxqebqbaQHWk=", "com.stock.shared.QuoteProxy");
withRawTypeToken("EV1JwOHpuX7ptqcTd9z4ExQGoas=", "com.stock.shared.RegistrationInfoProxy");
withRawTypeToken("InE$FIDuLzXv4Qf2BsU8aGiCBR8=", "com.stock.shared.StockProxy");
withRawTypeToken("Ws4L7iuBg9HiwyMgF3D6jbVRyUM=", "com.stock.shared.UserListProxy");
withClientToDomainMappings("com.stock.server.Message", Arrays.asList("com.stock.shared.MessageProxy"));
withClientToDomainMappings("com.stock.server.RegistrationInfo", Arrays.asList("com.stock.shared.RegistrationInfoProxy"));
withClientToDomainMappings("com.stock.server.entities.Quote", Arrays.asList("com.stock.shared.QuoteProxy"));
withClientToDomainMappings("com.stock.server.entities.Stock", Arrays.asList("com.stock.shared.StockProxy"));
withClientToDomainMappings("com.stock.server.entities.UserList", Arrays.asList("com.stock.shared.UserListProxy"));
}}
