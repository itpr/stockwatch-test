
package com.stock.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.stock.client.ClientFactory;
import com.stock.client.MyRequestFactory.UserListRequest;
import com.stock.shared.StockProxy;
import com.stock.shared.UserListProxy;


public class AddListActivity extends AbstractActivity implements AddListView.Presenter {
	
	private ClientFactory clientFactory;

	
	private String name;
	
	private AddListView view;

	public AddListActivity(Place place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getAddListView();
		view.setPresenter(this);
		
		clientFactory.getRequestFactory().stockRequest().getAll().fire(new Receiver<List<StockProxy>>(){

			@Override
			public void onSuccess(List<StockProxy> response) {
				view.setData(response);
			}});
		
		containerWidget.setWidget(view.asWidget());
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void onAddButtonClick() {
		List<Long> ids = new ArrayList<Long>();
		Set<StockProxy> set = view.getSelectedFeatures();
		String text = view.getTextBox();
		for(StockProxy sp:set){
			ids.add(sp.getId());
		}
		
		UserListRequest ulr = clientFactory.getRequestFactory().userListRequest();
		UserListProxy ul = ulr.create(UserListProxy.class);
		ul.setName(text);
		ul.setStocks(ids);
		ulr.save(ul).fire(new Receiver<Void>(){

			@Override
			public void onSuccess(Void response) {
				goTo(new ChartPlace("0"));
				
			}});
	}
}
