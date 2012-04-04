/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stock;

import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.stock.client.MyRequestFactory;
import com.stock.shared.StockProxy;
import com.stock.shared.UserListProxy;

public class StocksFragment extends ListFragment {

    private Activity ctx = null;
	private StockAdapter adapter;
	private AsyncFetchStock stock;
	private AsyncFetchUserList userList;
	private ListView listView;

	public StocksFragment(){
		
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = getListView();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setCacheColorHint(Color.TRANSPARENT);
        ctx = getActivity();
		Application app = ctx.getApplication();
		StockApplication stockApplication = (StockApplication) app;
		adapter = stockApplication.getAdapter(ctx);
		setListAdapter(adapter);
		
		SharedPreferences prefs = Util.getSharedPreferences(ctx);
		String deviceRegistrationID = prefs.getString(
				Util.DEVICE_REGISTRATION_ID, null);
		if (deviceRegistrationID != null) {
			fetchStocks(-1);
			fetchUserLists(-1);
		}
    }

   


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        
    }




    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    
	public void fetchStocks(long id) {

		if (stock != null) {
			stock.cancel(true);
		}
		stock = new AsyncFetchStock(ctx,this);
		stock.execute(id);
	}
	
	public void fetchUserLists(long id) {

		if (userList != null) {
			userList.cancel(true);
		}
		userList = new AsyncFetchUserList(ctx, this);
		userList.execute(id);
	}

	public void setUserLists(List<UserListProxy> items) {
		MyRequestFactory factory = Util.getRequestFactory(
				ctx, MyRequestFactory.class);
		UserListProxy all = factory.userListRequest().create(UserListProxy.class);
		all.setName("All");
		items.add(0, all);
		adapter.setUserLists(items);
	}
	
	public void addUserList(UserListProxy task) {
		adapter.addUserList(task);
		adapter.notifyDataSetChanged();
	}
	
	public int getUserListCount(){
		return adapter.getUserListCount();
	}
	
	public void setStocks(List<StockProxy> tasks) {
		adapter.setStocks(tasks);
		adapter.setAllStocks(tasks);
		adapter.notifyDataSetChanged();
	}

	public void addStocks(List<StockProxy> tasks) {
		adapter.addStocks(tasks);
		adapter.notifyDataSetChanged();
	}

	public void addStock(StockProxy task) {
		adapter.addStock(task);
		adapter.notifyDataSetChanged();
	}
	
	public StockAdapter getStockAdapter(){
		return adapter;
	} 


}
