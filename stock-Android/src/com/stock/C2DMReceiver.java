
package com.stock;

import com.google.android.c2dm.C2DMBaseReceiver;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class C2DMReceiver extends C2DMBaseReceiver {

    public C2DMReceiver() {
        super(Setup.SENDER_ID);
    }

    
    @Override
    public void onRegistered(Context context, String registration) {
        DeviceRegistrar.registerOrUnregister(context, registration, true);
    }

    
    @Override
    public void onUnregistered(Context context) {
        SharedPreferences prefs = Util.getSharedPreferences(context);
        String deviceRegistrationID = prefs.getString(Util.DEVICE_REGISTRATION_ID, null);
        DeviceRegistrar.registerOrUnregister(context, deviceRegistrationID, false);
    }

    
    @Override
    public void onError(Context context, String errorId) {
        context.sendBroadcast(new Intent(Util.UPDATE_UI_INTENT));
    }

    
    @Override
    public void onMessage(Context context, Intent intent) {
		StockApplication ta = (StockApplication) getApplication();
		ta.notifyListener(intent);
        //MessageDisplay.displayMessage(context, intent);
    }
}
