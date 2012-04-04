package com.stock.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.stock.client.view.ActionBarView;
import com.stock.client.view.ActionBarViewImpl;
import com.stock.client.view.AddListView;
import com.stock.client.view.AddListViewImpl;
import com.stock.client.view.ChartView;
import com.stock.client.view.ChartViewImpl;
import com.stock.client.view.StocksListView;
import com.stock.client.view.StocksListViewImpl;



public class ClientFactoryImpl implements ClientFactory {
	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	private static final ChartView chartView = new ChartViewImpl();
	private static final ActionBarView actionBarView = new ActionBarViewImpl();
	private static final StocksListView stocksListView = new StocksListViewImpl();
	private static final AddListView addListView = new AddListViewImpl();
	private static final MyRequestFactory reqf = GWT.create(MyRequestFactory.class);
	
	public ClientFactoryImpl(){
		reqf.initialize(getEventBus());
	}
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public ChartView getChartView() {
		return chartView;
	}

	@Override
	public ActionBarView getActionBarView() {
		return actionBarView;
	}

	@Override
	public StocksListView getStocksListView() {
		return stocksListView;
	}

	@Override
	public MyRequestFactory getRequestFactory() {
		return reqf;
	}

	@Override
	public AddListView getAddListView() {
		return addListView;
	}

}
