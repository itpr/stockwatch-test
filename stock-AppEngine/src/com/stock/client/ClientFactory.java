package com.stock.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.stock.client.view.ChartView;
import com.stock.client.view.ActionBarView;
import com.stock.client.view.StocksListView;
import com.stock.client.view.AddListView;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	public ChartView getChartView();
	public ActionBarView getActionBarView();
	public StocksListView getStocksListView();
	public MyRequestFactory getRequestFactory();
	public AddListView getAddListView();
}
