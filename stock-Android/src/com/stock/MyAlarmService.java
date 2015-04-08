package com.stock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.stock.StockApplication.StockListener;

public class MyAlarmService extends Service {



@Override

public void onCreate() {


}



@Override

public IBinder onBind(Intent intent) {	
	return null;

}



@Override

public void onDestroy() {

super.onDestroy();


}



@Override

public void onStart(Intent intent, int startId) {


	super.onStart(intent, startId);
	if(startId>1){
		StockApplication stockApplication = (StockApplication)this.getApplication();
		StockListener listener =stockApplication.getStockListener();
		listener.onStockUpdated(-1);
	}
}



@Override

public boolean onUnbind(Intent intent) {

return super.onUnbind(intent);

}



}
