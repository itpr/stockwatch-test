
package com.stock.client.view;

import com.stock.client.ClientFactory;
import com.stock.client.view.ActionBarView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class ActionBarActivity extends AbstractActivity implements ActionBarView.Presenter {
	
	private ClientFactory clientFactory;

	
	private String name;

	public ActionBarActivity(Place place, ClientFactory clientFactory) {
		this.name = ((ChartPlace)place).getName();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ActionBarView view = clientFactory.getActionBarView();
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
	}

	
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
