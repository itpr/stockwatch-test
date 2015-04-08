package com.stock;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class StockApplication extends Application {

	interface UserListListener {
		void onUserListUpdated(long id);
	}
	
	interface StockListener{
		void onStockUpdated(long id);
	}

	private UserListListener listener;
	private StockListener stockListener;
	
	public StockListener getStockListener() {
		return stockListener;
	}

	public void setStockListener(StockListener stockListener) {
		this.stockListener = stockListener;
	}

	private StockAdapter adapter;
	private UserStockAdapter userAdapter;
	
	public void setUserListListener(UserListListener listener) {
		this.listener = listener;
	}
	
	public UserListListener getUserListListener(){
		return listener;
	}

	public StockAdapter getAdapter(Context context) {
		if (adapter == null) {
			adapter = new StockAdapter(context);
		}

		return adapter;
	}
	
	public UserStockAdapter getUserStockAdapter(Context context) {
		if (userAdapter == null) {
			userAdapter = new UserStockAdapter(context);
		}

		return userAdapter;
	}

	public void notifyListener(Intent intent) {
		if (listener != null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				String message = (String) extras.get("message");
				listener.onUserListUpdated(Long.parseLong(message));
			}
		}
	}
}
