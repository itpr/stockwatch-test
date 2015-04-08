
package com.stock.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.stock.client.mapper.ActionBarActivityMapper;
import com.stock.client.mapper.AppPlaceHistoryMapper;
import com.stock.client.mapper.ChartActivityMapper;
import com.stock.client.mapper.StocksListActivityMapper;
import com.stock.client.view.ChartPlace;
import com.stock.client.view.OneWidgetLayoutPanel;


public class Stock implements EntryPoint {

	interface Binder extends UiBinder<DockLayoutPanel, Stock> { }
	private static final Binder binder = GWT.create(Binder.class);
	
	private Place defaultPlace = new ChartPlace("0");
	
	@UiField OneWidgetLayoutPanel actionBarPanel;
	@UiField OneWidgetLayoutPanel stocksListPanel;
	@UiField OneWidgetLayoutPanel chartPanel; 
	
  public void onModuleLoad() {
	  DockLayoutPanel outer = binder.createAndBindUi(this);
	  

	  
	  ClientFactory clientFactory = GWT.create(ClientFactory.class);
	  EventBus eventBus = clientFactory.getEventBus();
	  PlaceController placeController = clientFactory.getPlaceController();

	  final MyRequestFactory rf = clientFactory.getRequestFactory();
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	
	  ActivityMapper chartActivityMapper = new ChartActivityMapper(clientFactory);
	  ActivityManager chartActivityManager = new ActivityManager(chartActivityMapper, eventBus);
	  chartActivityManager.setDisplay(chartPanel);
	  
	  ActivityMapper actionBarActivityMapper = new ActionBarActivityMapper(clientFactory);
	  ActivityManager actionBarActivityManager = new ActivityManager(actionBarActivityMapper, eventBus);
	  actionBarActivityManager.setDisplay(actionBarPanel);
	  
	  ActivityMapper stocksListActivityMapper = new StocksListActivityMapper(clientFactory);
	  ActivityManager stocksListActivityManager = new ActivityManager(stocksListActivityMapper, eventBus);
	  stocksListActivityManager.setDisplay(stocksListPanel);
	  
	  AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
	  PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
	  historyHandler.register(placeController, eventBus, defaultPlace);
	  
	  RootLayoutPanel.get().add(outer);
	  
	  historyHandler.handleCurrentHistory();
  }
}
