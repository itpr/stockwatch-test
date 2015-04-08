package com.stock.client.mapper;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.stock.client.ClientFactory;
import com.stock.client.view.AddListActivity;
import com.stock.client.view.AddListPlace;
import com.stock.client.view.ChartActivity;
import com.stock.client.view.ChartPlace;

public class ChartActivityMapper implements ActivityMapper {
	private ClientFactory clientFactory;
	
	public ChartActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	public Activity getActivity(Place place) {
		
		if(place instanceof AddListPlace){
			return new AddListActivity(place, clientFactory);
		}
		return new ChartActivity(place, clientFactory);
	}



}
