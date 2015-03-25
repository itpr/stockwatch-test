
package com.stock.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyForName("com.stock.server.RegistrationInfo")
public interface RegistrationInfoProxy extends ValueProxy {
  String getDeviceId();
  String getDeviceRegistrationId();
  void setDeviceId(String deviceId);
  void setDeviceRegistrationId(String deviceRegistrationId);
}
