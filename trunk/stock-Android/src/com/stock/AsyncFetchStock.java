package com.stock;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.stock.client.MyRequestFactory;
import com.stock.shared.StockProxy;

public class AsyncFetchStock extends AsyncTask<Long, Void, List<StockProxy>> {

	private final Activity activity;
	private StocksFragment fragment;
	private boolean newStock = false;

	public AsyncFetchStock(Activity activity, StocksFragment fragment) {
		super();
		this.activity = activity;
		this.fragment = fragment;
	}

	@Override
	protected List<StockProxy> doInBackground(Long... arguments) {
		final List<StockProxy> list = new ArrayList<StockProxy>();

		MyRequestFactory factory = Util.getRequestFactory(
				activity, MyRequestFactory.class);

		if (arguments.length == 0 || arguments[0] == -1) {
			factory.stockRequest().getAllWithChildren().with("quote")
			.fire(new Receiver<List<StockProxy>>() {

				@Override
				public void onFailure(ServerFailure error){
					String ss =error.getMessage();
					String sss = error.getStackTraceString();
					String ssss = error.toString();
				}
				
				
				@Override
				public void onSuccess(List<StockProxy> arg0) {
					list.addAll(arg0);

				}
			});
		} else {
			newStock = true;
			/*factory.stockWatchRequest().readStock(arguments[0])
					.fire(new Receiver<StockProxy>() {

						@Override
						public void onSuccess(StockProxy arg0) {
							list.add(arg0);

						}
					});*/
		}

		return list;
	}

	@Override
	protected void onPostExecute(List<StockProxy> result) {
		if (newStock) {
			fragment.addStocks(result);
		} else {
			fragment.setStocks(result);
		}
	}
}
