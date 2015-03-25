
package com.stock.server;

import com.google.android.c2dm.server.PMF;
import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DeviceInfo {
    private static final Logger log =
        Logger.getLogger(DeviceInfo.class.getName());

    
    @PrimaryKey
    @Persistent
    private Key key;

    
    @Persistent
    private String deviceRegistrationID;

    
    @Persistent
    private String type;

    
    @Persistent
    private Date registrationTimestamp;

    @Persistent
    private Boolean debug;

    public DeviceInfo(Key key, String deviceRegistrationID) {
        log.info("new DeviceInfo: key=" + key + ", deviceRegistrationId=" + deviceRegistrationID);
        this.key = key;
        this.deviceRegistrationID = deviceRegistrationID;
        this.setRegistrationTimestamp(new Date()); // now
    }

    public DeviceInfo(Key key) {
        log.info("new DeviceInfo: key=" + key);
        this.key = key;
    }

    public Key getKey() {
        log.info("DeviceInfo: return key=" + key);
        return key;
    }

    public void setKey(Key key) {
        log.info("DeviceInfo: set key=" + key);
        this.key = key;
    }

    // Accessor methods for properties added later (hence can be null)

    public String getDeviceRegistrationID() {
        log.info("DeviceInfo: return deviceRegistrationID=" + deviceRegistrationID);
        return deviceRegistrationID;
    }

    public void setDeviceRegistrationID(String deviceRegistrationID) {
        log.info("DeviceInfo: set deviceRegistrationID=" + deviceRegistrationID);
        this.deviceRegistrationID = deviceRegistrationID;
    }

    public boolean getDebug() {
        return (debug != null ? debug.booleanValue() : false);
    }

    public void setDebug(boolean debug) {
        this.debug = new Boolean(debug);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type != null ? type : "";
    }

    public void setRegistrationTimestamp(Date registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public Date getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    
    @SuppressWarnings("unchecked")
    public static List<DeviceInfo> getDeviceInfoForUser(String user) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
          // Canonicalize user name
          user = user.toLowerCase(Locale.ENGLISH);
          Query query = pm.newQuery(DeviceInfo.class);
          query.setFilter("key >= '" +
              user + "' && key < '" + user + "$'");
          List<DeviceInfo> qresult = (List<DeviceInfo>) query.execute();
          // Copy to array - we need to close the query
          List<DeviceInfo> result = new ArrayList<DeviceInfo>();
          for (DeviceInfo di : qresult) {
            result.add(di);
          }
          query.closeAll();
          log.info("Return " + result.size() + " devices for user " + user);
          return result;
        } finally {
          pm.close();
        }
    }

    @Override
    public String toString() {
      return "DeviceInfo[key=" + key + ", deviceRegistrationID="
          + deviceRegistrationID + ", type=" + type
          + ", registrationTimestamp=" + registrationTimestamp + ", debug="
          + debug + "]";
    }
}
