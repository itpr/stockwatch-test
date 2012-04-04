package com.stock;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.Tab;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.stock.StockApplication.StockListener;
import com.stock.StockApplication.UserListListener;
import com.stock.shared.UserListProxy;

public class MainActivity extends FragmentActivity implements OnItemClickListener{

	private static final String TAG = "MainActivity";

	/**
	 * The current context.
	 */
	private Context mContext = this;
	private ActionBar actionBar;

	/**
	 * A {@link BroadcastReceiver} to receive the response from a register or
	 * unregister request, and to update the UI.
	 */
	private final BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String accountName = intent
					.getStringExtra(DeviceRegistrar.ACCOUNT_NAME_EXTRA);
			int status = intent.getIntExtra(DeviceRegistrar.STATUS_EXTRA,
					DeviceRegistrar.ERROR_STATUS);
			String message = null;
			String connectionStatus = Util.DISCONNECTED;
			if (status == DeviceRegistrar.REGISTERED_STATUS) {
				message = getResources().getString(
						R.string.registration_succeeded);
				connectionStatus = Util.CONNECTED;
			} else if (status == DeviceRegistrar.UNREGISTERED_STATUS) {
				message = getResources().getString(
						R.string.unregistration_succeeded);
			} else {
				message = getResources().getString(R.string.registration_error);
			}

			// Set connection status
			SharedPreferences prefs = Util.getSharedPreferences(mContext);
			prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus)
					.commit();

			// Display a notification
			Util.generateNotification(mContext,
					String.format(message, accountName));
		}
	};
	



	/**
	 * Begins the activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.stocklist);
		NavigationFragment frag = (NavigationFragment)getSupportFragmentManager().findFragmentById(R.id.frag_navigation);
		StocksFragment frag1 = (StocksFragment)getSupportFragmentManager().findFragmentById(R.id.frag_navigation);
		AddUserListFragment frag2 = (AddUserListFragment)getSupportFragmentManager().findFragmentById(R.id.frag_navigation);
		UserStocksFragment frag3 = (UserStocksFragment)getSupportFragmentManager().findFragmentById(R.id.frag_navigation);
		
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		
		Tab tab = actionBar.newTab().setText("Stocks").setTabListener(
				new StocksTabListener(this, frag, frag1));

		
		actionBar.addTab(tab);
		
		tab = actionBar.newTab().setText("Add UserList").setTabListener(
				new UserStocksTabListener(this, frag2, frag3));
		
		actionBar.addTab(tab);
		
		StockApplication stApp = (StockApplication) getApplication();
		stApp.setUserListListener(new UserListListener() {
			@Override
			public void onUserListUpdated(final long id) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						StocksFragment frag = (StocksFragment) getSupportFragmentManager().findFragmentByTag("stockFrag");
						frag.fetchUserLists(id);
					}
				});

			}
		});
		
		stApp.setStockListener(new StockListener() {
			@Override
			public void onStockUpdated(final long id) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						StocksFragment frag = (StocksFragment) getSupportFragmentManager().findFragmentByTag("stockFrag");
						frag.fetchStocks(id);
					}
				});

			}
		});
		

		Intent myIntent = new Intent(MainActivity.this, MyAlarmService.class);

		PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);

		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(System.currentTimeMillis());

		calendar.add(Calendar.SECOND, 180);
          
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 3*60*1000, pendingIntent);
		       

		 


		// Register a receiver to provide register/unregister notifications
		registerReceiver(mUpdateUIReceiver, new IntentFilter(
				Util.UPDATE_UI_INTENT));
	}
	
    private class StocksTabListener implements ActionBar.TabListener {
        private NavigationFragment mFragment;
        private StocksFragment mFragment1;
        private Activity mActivity;
        

        public StocksTabListener(Activity a,NavigationFragment fragment, StocksFragment ff) {
        	mActivity = a;
            mFragment = fragment;
            mFragment1 = ff;
        }

        @Override
		public void onTabSelected(Tab tab, FragmentTransaction ift) {
		    FragmentManager fragMgr = ((FragmentActivity)mActivity).getSupportFragmentManager();
		    FragmentTransaction ft = fragMgr.beginTransaction();
		    if(mFragment==null){
		    	mFragment = new NavigationFragment();
            	ft.add(R.id.main, mFragment);
		    }else
		    	ft.attach(mFragment);
		    if(mFragment1==null){
		    	mFragment1 = new StocksFragment();
            	ft.add(R.id.main, mFragment1,"stockFrag");
		    }else
		    	ft.attach(mFragment1);
            ft.commit();
        }

        @Override
		public void onTabUnselected(Tab tab, FragmentTransaction ift) {
		    FragmentManager fragMgr = ((FragmentActivity)mActivity).getSupportFragmentManager();
		    FragmentTransaction ft = fragMgr.beginTransaction();
            ft.detach(mFragment);
            ft.detach(mFragment1);
            ft.commit();
        }

        @Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
            
        }

    }


    private class UserStocksTabListener implements ActionBar.TabListener {
        private AddUserListFragment mFragment;
        private UserStocksFragment mFragment1;
        private Activity mActivity;
        

        public UserStocksTabListener(Activity a,AddUserListFragment fragment, UserStocksFragment ff) {
        	mActivity = a;
            mFragment = fragment;
            mFragment1 = ff;
        }

        @Override
		public void onTabSelected(Tab tab, FragmentTransaction ift) {
		    FragmentManager fragMgr = ((FragmentActivity)mActivity).getSupportFragmentManager();
		    FragmentTransaction ft = fragMgr.beginTransaction();
		    if(mFragment==null){
		    	mFragment = new AddUserListFragment();
            	ft.add(R.id.main, mFragment);
		    }else
		    	ft.attach(mFragment);
		    if(mFragment1==null){
		    	mFragment1 = new UserStocksFragment();
            	ft.add(R.id.main, mFragment1,"userStockFrag");
		    }else
		    	ft.attach(mFragment1);
            ft.commit();
        }

        @Override
		public void onTabUnselected(Tab tab, FragmentTransaction ift) {
		    FragmentManager fragMgr = ((FragmentActivity)mActivity).getSupportFragmentManager();
		    FragmentTransaction ft = fragMgr.beginTransaction();
            ft.detach(mFragment);
            ft.detach(mFragment1);
            ft.commit();
        }

        @Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
            
        }

    }
    
	

	@Override
	public void onResume() {
		super.onResume();

		SharedPreferences prefs = Util.getSharedPreferences(mContext);
		String connectionStatus = prefs.getString(Util.CONNECTION_STATUS,
				Util.DISCONNECTED);
		if (Util.DISCONNECTED.equals(connectionStatus)) {
			startActivity(new Intent(this, AccountsActivity.class));
		}

	}

	/**
	 * Shuts down the activity.
	 */
	@Override
	public void onDestroy() {
		unregisterReceiver(mUpdateUIReceiver);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		// Invoke the Register activity
		menu.getItem(0).setIntent(new Intent(this, AccountsActivity.class));
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();


	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}
	
	public void onNext(View view){
		StocksFragment frag = (StocksFragment) getSupportFragmentManager().findFragmentByTag("stockFrag");
		StockAdapter adapter = frag.getStockAdapter();
		if(adapter.getUserListCount()>1){
			TextView textView = (TextView) findViewById(R.id.textView1);
			adapter.onNextClick();
			textView.setText(adapter.getUserListName());
			adapter.notifyDataSetChanged();
		}
	}
	
	public void onPrev(View view){
		StocksFragment frag = (StocksFragment) getSupportFragmentManager().findFragmentByTag("stockFrag");
		StockAdapter adapter = frag.getStockAdapter();
		if(adapter.getUserListCount()>1){
			TextView textView = (TextView) findViewById(R.id.textView1);
			adapter.onPrevClick();
			textView.setText(adapter.getUserListName());
			adapter.notifyDataSetChanged();
		}
	}
	
	public void onAdd(View view){
		UserStocksFragment frag = (UserStocksFragment) getSupportFragmentManager().findFragmentByTag("userStockFrag");
		UserStockAdapter adapter = frag.getStockAdapter();
		TextView name = (TextView) findViewById(R.id.editText1);
		CharSequence nm = name.getText();
		AsyncAddUserList addUsr = new AsyncAddUserList(this,adapter.getSelected(), nm.toString());
		addUsr.execute();
	}

	public void setSelectedTab(int i){
		actionBar.getTabAt(i).select();	
	}
	
	public void addUserList(UserListProxy usr){
		StocksFragment frag = (StocksFragment) getSupportFragmentManager().findFragmentByTag("stockFrag");
		StockAdapter adapter = frag.getStockAdapter();
		adapter.addUserList(usr);
		adapter.notifyDataSetChanged();
	}

}
