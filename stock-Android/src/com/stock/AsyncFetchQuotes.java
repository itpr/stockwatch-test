package com.stock;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.stock.client.MyRequestFactory;
import com.stock.shared.QuoteProxy;
import com.stock.shared.charts.TimePeriod;

public class AsyncFetchQuotes extends AsyncTask<Long, Void, List<QuoteProxy>> {

	private final Activity activity;
	private final ChartFragment fragment;
	

	public AsyncFetchQuotes(Activity activity, ChartFragment fragment) {
		super();
		this.activity = activity;
		this.fragment = fragment;
	}

	@Override
	protected List<QuoteProxy> doInBackground(Long... arguments) {
		final List<QuoteProxy> list = new ArrayList<QuoteProxy>();

		MyRequestFactory factory = Util.getRequestFactory(
				activity, MyRequestFactory.class);


			factory.quoteRequest().getAllById(arguments[0], TimePeriod.ONE_MINUTE).fire(new Receiver<List<QuoteProxy>>() {

				@Override
				public void onFailure(ServerFailure error){
					
				}
				
				@Override
				public void onSuccess(List<QuoteProxy> arg0) {
					list.addAll(arg0);
					
				}
			});

		return list;
	}

	@Override
	protected void onPostExecute(List<QuoteProxy> result) {
		fragment.setQuotes(result);
	}
}
