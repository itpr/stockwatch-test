package com.stock;

import java.util.ArrayList;
import java.util.List;

import com.stock.shared.QuoteProxy;
import com.stock.shared.StockProxy;
import com.stock.shared.UserListProxy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StockAdapter extends BaseAdapter {

	private final static class ViewHolder {
		TextView title;
	}

	private final List<StockProxy> items = new ArrayList<StockProxy>();
	private final List<StockProxy> allStocks = new ArrayList<StockProxy>();
	private final List<UserListProxy> userLists = new ArrayList<UserListProxy>();
	private final LayoutInflater inflater;
	private int currentList = 0;

	public StockAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public void setAllStocks(List<StockProxy> stocks){
		this.allStocks.clear();
		this.allStocks.addAll(stocks);
	}
	
	public void setStocks(List<StockProxy> items) {
		this.items.clear();
		this.items.addAll(items);

	}
	
	public List<StockProxy> getStocks(){
		return this.allStocks;
	}
	
	public void setUserLists(List<UserListProxy> items) {
		this.userLists.clear();
		this.userLists.addAll(items);
	}
	
	public void addUserList(UserListProxy list){
		this.userLists.add(list);
	}
	
	public int getUserListCount(){
		return this.userLists.size();
	}

	public void addStocks(List<StockProxy> items) {
		this.items.addAll(items);
	}

	public void addStock(StockProxy item) {
		this.items.add(item);
	}

	public StockProxy get(int position) {
		return items.get(position);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public int getStocksCount(){
		return allStocks.size();
	}
	
	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public String getUserListName(){
		return userLists.get(currentList).getName();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup view) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listitem, null);

			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.taskTitle);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		StockProxy task = items.get(position);
		QuoteProxy qt = task.getQuote();
		if(qt != null){
			((TextView) convertView.findViewById(R.id.textView1)).setText(qt.getClose().toString());
			((TextView) convertView.findViewById(R.id.textView2)).setText(qt.getDate().toGMTString());
		}
		holder.title.setText(task.getName());

		return convertView;
	}

	public void onNextClick() {
		currentList++;
		if(currentList+1>userLists.size()){
			currentList = 0;
		}
		if(currentList == 0){
			setStocks(allStocks);
		}else{
			fillStockList(userLists.get(currentList).getStocks());
		}
	}
	
	public void onPrevClick(){
		currentList--;
		if(currentList<0){
			currentList = userLists.size()-1;
		}
		if(currentList==0){
			setStocks(allStocks);
		}else{
			fillStockList(userLists.get(currentList).getStocks());
		}
	}

	private void fillStockList(List<Long> stocks) {
		List<StockProxy> tmp = new ArrayList<StockProxy>();
		for(Long i : stocks){
			for(StockProxy sp:allStocks){
				if(sp.getId().equals(i)){
					tmp.add(sp);
				}
			}
		}
		setStocks(tmp);	
	}
}
