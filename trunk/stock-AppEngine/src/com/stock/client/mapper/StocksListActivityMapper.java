package com.stock.client.mapper;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.stock.client.ClientFactory;
import com.stock.client.view.ActionBarActivity;
import com.stock.client.view.ChartActivity;
import com.stock.client.view.ChartPlace;
import com.stock.client.view.StocksListActivity;

public class StocksListActivityMapper implements ActivityMapper {
	private ClientFactory clientFactory;
	
	public StocksListActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	public Activity getActivity(Place place) {
		
			return new StocksListActivity(new ChartPlace("0"), clientFactory);
		
	}



}
