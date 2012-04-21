/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.stock.client.view;

import java.util.ArrayList;
import java.util.List;

import com.stock.client.ClientFactory;
import com.stock.client.MyRequestFactory.UserListRequest;
import com.stock.client.view.StocksListView;
import com.stock.server.entities.UserList;
import com.stock.shared.StockProxy;
import com.stock.shared.UserListProxy;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class StocksListActivity extends AbstractActivity implements StocksListView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
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
	    
	    /*Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
	        @Override
			public boolean execute() {
	        	fillStockList();
	          return true;
	        }
	      }, 1000*60*3);*/
		
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
				/*List<Long> list = new ArrayList<Long>();
				for(StockProxy sp:response){
					list.add(sp.getId());
				}
				UserListRequest tr = clientFactory.getRequestFactory().userListRequest();
				UserListProxy tp = tr.create(UserListProxy.class);
				tp.setStocks(list);
				tr.save(tp).with("stocks").fire();*/
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

	/**
	 * @see StocksListView.Presenter#goTo(Place)
	 */
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
