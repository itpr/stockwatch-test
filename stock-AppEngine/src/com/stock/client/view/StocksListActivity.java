
package com.stock.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.stock.client.ClientFactory;
import com.stock.shared.StockProxy;
import com.stock.shared.UserListProxy;


public class StocksListActivity extends AbstractActivity implements StocksListView.Presenter {
	
	private ClientFactory clientFactory;

	
	private String name;
	
	private List<UserListProxy> userLists = new ArrayList<UserListProxy>();
	
	private List<StockProxy> allStocks;
	
	private int currentList = 0;
	
	private StocksListView view;

	public StocksListActivity(Place place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getStocksListView();
		view.setPresenter(this);
		fillStockList();
		getAllUserLists();
		containerWidget.setWidget(view.asWidget());
		
	    Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
	        @Override
			public boolean execute() {
	        	getAllUserLists();
	          return true;
	        }
	      }, 3000);
	    
	    
		
	}
	
	@Override
	public void onStop(){
		view.unselect();
	}


	private void getAllUserLists() {
		clientFactory.getRequestFactory().userListRequest().getAll().fire(new Receiver<List<UserListProxy>>(){

			@Override
			public void onSuccess(List<UserListProxy> response) {
				userLists.clear();
				UserListProxy all =clientFactory.getRequestFactory().userListRequest().create(UserListProxy.class);
				all.setName("All");
				userLists.add(all);
				userLists.addAll(response);
				
			}});
		
	}

	private void fillStockList() {
		clientFactory.getRequestFactory().stockRequest().getAllWithChildren().with("quote")
		.fire(new Receiver<List<StockProxy>>(){
			@Override
			public void onSuccess(List<StockProxy> response) {
				allStocks = response;
				view.setStocksData(response);
				
			}});
		
	}
	
	private void fillStockList(List<Long> stocks){
		List<StockProxy> tmp = new ArrayList<StockProxy>();
		for(Long i : stocks){
			for(StockProxy sp:allStocks){
				if(sp.getId().equals(i)){
					tmp.add(sp);
				}
			}
		}
		view.setStocksData(tmp);
	}

	
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void onNextButtonClick() {
		currentList++;
		if(currentList+1>userLists.size()){
			currentList = 0;
		}
		if(currentList == 0){
			view.setStocksData(allStocks);
			view.setListName("All");
		}else{
			fillStockList(userLists.get(currentList).getStocks());
			view.setListName(userLists.get(currentList).getName());
		}
	}
	
	@Override
	public void onPrevButtonClick(){
		currentList--;
		if(currentList<0){
			currentList = userLists.size()-1;
		}
		if(currentList==0){
			view.setStocksData(allStocks);
			view.setListName("All");
		}else{
			fillStockList(userLists.get(currentList).getStocks());
			view.setListName(userLists.get(currentList).getName());
		}
	}

	@Override
	public void onCellSelected(long id) {
		goTo(new ChartPlace(Long.toString(id)));
	}
	
}
