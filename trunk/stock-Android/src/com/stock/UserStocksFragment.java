

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
