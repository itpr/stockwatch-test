package com.stock;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.stock.client.MyRequestFactory;
import com.stock.shared.UserListProxy;

public class AsyncFetchUserList extends AsyncTask<Long, Void, List<UserListProxy>> {

	private final Activity activity;
	private final StocksFragment fragment;
	

	public AsyncFetchUserList(Activity activity, StocksFragment fragment) {
		super();
		this.activity = activity;
		this.fragment = fragment;
	}

	@Override
	protected List<UserListProxy> doInBackground(Long... arguments) {
		final List<UserListProxy> list = new ArrayList<UserListProxy>();

		MyRequestFactory factory = Util.getRequestFactory(
				activity, MyRequestFactory.class);

		if (arguments.length == 0 || arguments[0] == -1) {
			factory.userListRequest().getAll()
			.fire(new Receiver<List<UserListProxy>>() {

				@Override
				public void onFailure(ServerFailure error){
					String ss =error.getMessage();
					String sss = error.getStackTraceString();
					String ssss = error.toString();
					
				}
				
				@Override
				public void onSuccess(List<UserListProxy> arg0) {
					list.addAll(arg0);
					
				}
			});
		} else {
			factory.userListRequest().get(arguments[0])
			.fire(new Receiver<UserListProxy>(){

				@Override
				public void onSuccess(UserListProxy arg0) {
					list.add(arg0);
					
				}});
		}

		return list;
	}

	@Override
	protected void onPostExecute(List<UserListProxy> result) {
		if(fragment.getUserListCount()>0 && result.size()>0){
			fragment.addUserList(result.get(0));
		}else{
			fragment.setUserLists(result);
		}
	}
}
