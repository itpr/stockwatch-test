
package com.stock;

import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.vm.RequestFactorySource;
import com.stock.R;
import com.stock.R.drawable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;


public class Util {

    
    private static final String TAG = "Util";

    // Shared constants

    
    public static final String ACCOUNT_NAME = "accountName";

    
    public static final String AUTH_COOKIE = "authCookie";

    
    public static final String CONNECTION_STATUS = "connectionStatus";

    
    public static final String CONNECTED = "connected";

    
    public static final String CONNECTING = "connecting";

    
    public static final String DISCONNECTED = "disconnected";

    
    public static final String DEVICE_REGISTRATION_ID = "deviceRegistrationID";

    
    public static final String RF_METHOD = "/gwtRequest";

    
    public static final String UPDATE_UI_INTENT = getPackageName() + ".UPDATE_UI";

    // End shared constants

    
    private static final String SHARED_PREFS = "stock".toUpperCase(Locale.ENGLISH) + "_PREFS";

    
    private static final Map<Context, String> URL_MAP = new HashMap<Context, String>();

    
    public static void generateNotification(Context context, String message) {
        int icon = R.drawable.status_icon;
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, message, when);
        notification.setLatestEventInfo(context, "C2DM Example", message,
                PendingIntent.getActivity(context, 0, null, PendingIntent.FLAG_CANCEL_CURRENT));
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        SharedPreferences settings = Util.getSharedPreferences(context);
        int notificatonID = settings.getInt("notificationID", 0);

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(notificatonID, notification);

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("notificationID", ++notificatonID % 32);
        editor.commit();
    }

    
    public static String getBaseUrl(Context context) {
        String url = URL_MAP.get(context);
        if (url == null) {
            // if a debug_url raw resource exists, use its contents as the url
            url = getDebugUrl(context);
            // otherwise, use the production url
            if (url == null) {
                url = Setup.PROD_URL;
            }
            URL_MAP.put(context, url);
        }
        return url;
    }

    
    public static <T extends RequestFactory> T getRequestFactory(Context context,
            Class<T> factoryClass) {
        T requestFactory = RequestFactorySource.create(factoryClass);

        SharedPreferences prefs = getSharedPreferences(context);
        String authCookie = prefs.getString(Util.AUTH_COOKIE, null);

        String uriString = Util.getBaseUrl(context) + RF_METHOD;
        URI uri;
        try {
            uri = new URI(uriString);
        } catch (URISyntaxException e) {
            Log.w(TAG, "Bad URI: " + uriString, e);
            return null;
        }
        requestFactory.initialize(new SimpleEventBus(),
                new AndroidRequestTransport(uri, authCookie));

        return requestFactory;
    }

    
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS, 0);
    }

    
    public static boolean isDebug(Context context) {
        // Although this is a bit roundabout, it has the nice side effect
        // of caching the result.
        return !Setup.PROD_URL.equals(getBaseUrl(context));
    }

    
    private static String getDebugUrl(Context context) {
        BufferedReader reader = null;
        String url = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("debugging_prefs.properties");
            reader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String s = reader.readLine();
                if (s == null) {
                    break;
                }
                if (s.startsWith("url=")) {
                    url = s.substring(4).trim();
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            // O.K., we will use the production server
            return null;
        } catch (Exception e) {
            Log.w(TAG, "Got exception " + e);
            Log.w(TAG, Log.getStackTraceString(e));
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.w(TAG, "Got exception " + e);
                    Log.w(TAG, Log.getStackTraceString(e));
                }
            }
        }

        return new String("http://10.0.2.2:8888");
    }

    
    private static String getPackageName() {
        return Util.class.getPackage().getName();
    }
}
