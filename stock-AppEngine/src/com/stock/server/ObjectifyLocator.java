package com.stock.server;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.stock.server.entities.DatastoreObject;



public class ObjectifyLocator extends Locator<DatastoreObject, Long> {
  @Override
  public DatastoreObject create(Class<? extends DatastoreObject> clazz) {
    try {
      return clazz.newInstance();
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DatastoreObject find(Class<? extends DatastoreObject> clazz, Long id) {
    return null;
  }

  @Override
  public Class<DatastoreObject> getDomainType() {
    // Never called
    return null;
  }

  @Override
  public Long getId(DatastoreObject domainObject) {
    return domainObject.getId();
  }

  @Override
  public Class<Long> getIdType() {
    return Long.class;
  }

  @Override
  public Object getVersion(DatastoreObject domainObject) {
    return domainObject.getVersion();
  }
}
