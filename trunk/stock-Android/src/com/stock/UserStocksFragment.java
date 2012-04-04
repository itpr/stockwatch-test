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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.stock.shared.StockProxy;

public class UserStocksFragment extends ListFragment {

    private Activity ctx = null;
	private UserStockAdapter adapter;
	private ListView listView;

	public UserStocksFragment(){
		
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = getListView();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setCacheColorHint(Color.TRANSPARENT);
        ctx = getActivity();
		Application app = ctx.getApplication();
		StockApplication stockApplication = (StockApplication) app;
		
		adapter = stockApplication.getUserStockAdapter(ctx);
		setListAdapter(adapter);
		setStocks(stockApplication.getAdapter(ctx).getStocks());
		if(stockApplication.getAdapter(ctx).getStocksCount()==0){
			((MainActivity)ctx).setSelectedTab(0);
		}
    }

   


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        
    }




    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    
	
	public void setStocks(List<StockProxy> tasks) {
		adapter.setStocks(tasks);
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
	
	public UserStockAdapter getStockAdapter(){
		return adapter;
	} 


}
