package com.stock;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.stock.client.MyRequestFactory;
import com.stock.client.MyRequestFactory.UserListRequest;
import com.stock.shared.StockProxy;
import com.stock.shared.UserListProxy;

public class AsyncAddUserList extends AsyncTask<Long, Void, UserListProxy> {

	private final MainActivity activity;
	private final List<StockProxy> stocks;
	private final String name;
	

	public AsyncAddUserList(MainActivity activity, List<StockProxy> stocks, String name) {
		super();
		this.activity = activity;
		this.stocks = stocks;
		this.name = name;
	}

	@Override
	protected UserListProxy doInBackground(Long... arguments) {

		MyRequestFactory factory = Util.getRequestFactory(
				activity, MyRequestFactory.class);

		UserListRequest req = factory.userListRequest();
		UserListProxy usrList = req.create(UserListProxy.class);
		usrList.setName(name);
		List<Long> ids = new ArrayList<Long>();
		for(StockProxy sp:stocks){
			ids.add(sp.getId());
		}
		usrList.setStocks(ids);
		if (arguments.length == 0 || arguments[0] == -1) {
			req.save(usrList).fire();
		}
		return usrList;
	}
	
	@Override
	protected void onPostExecute(UserListProxy h) {
		activity.addUserList(h);
		activity.setSelectedTab(0);
	}
		

}
