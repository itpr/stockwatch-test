package com.stock;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stock.shared.StockProxy;

public class UserStockAdapter extends BaseAdapter {

	private final static class ViewHolder {
		TextView title;
		CheckBox checkbox;
	}

	private final List<StockProxy> items = new ArrayList<StockProxy>();
	private final LayoutInflater inflater;
	private final List<StockProxy> selected = new ArrayList<StockProxy>();

	public UserStockAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public void setStocks(List<StockProxy> items) {
		this.items.clear();
		this.items.addAll(items);

	}

	public void addStocks(List<StockProxy> items) {
		this.items.addAll(items);
	}

	public void addStock(StockProxy item) {
		this.items.add(item);
	}
	
	public List<StockProxy> getSelected(){
		return selected;
	}

	public StockProxy get(int position) {
		return items.get(position);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup view) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.userlistitem, null);

			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.stockName);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		StockProxy task = items.get(position);
		holder.title.setText(task.getName());
		holder.checkbox.setOnClickListener(new OnItemClickListener(task));
		
		return convertView;
	}
	
	private class OnItemClickListener implements OnClickListener{           
        private StockProxy stock;
        OnItemClickListener(StockProxy stock){
                this.stock = stock;
        }
        @Override
        public void onClick(View arg0) {
        		if(selected.remove(stock)==false){
        			selected.add(stock);      
        		}
        }               
    }

}
