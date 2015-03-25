
package com.stock.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyForName(value = "com.stock.server.Message",
    locator = "com.stock.server.MessageLocator")
public interface MessageProxy extends ValueProxy {
  String getMessage();
  String getRecipient();
  void setRecipient(String recipient);
  void setMessage(String message);
}
