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
package com.stock.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.stock.client.MyRequestFactory.QuoteRequest;
import com.stock.client.MyRequestFactory.StockRequest;
import com.stock.client.mapper.ActionBarActivityMapper;
import com.stock.client.mapper.AppPlaceHistoryMapper;
import com.stock.client.mapper.ChartActivityMapper;
import com.stock.client.mapper.StocksListActivityMapper;
import com.stock.client.view.ChartPlace;
import com.stock.client.view.OneWidgetLayoutPanel;
import com.stock.shared.QuoteProxy;
import com.stock.shared.StockProxy;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Stock implements EntryPoint {

	interface Binder extends UiBinder<DockLayoutPanel, Stock> { }
	private static final Binder binder = GWT.create(Binder.class);
	
	private Place defaultPlace = new ChartPlace("1");
	
	@UiField OneWidgetLayoutPanel actionBarPanel;
	@UiField OneWidgetLayoutPanel stocksListPanel;
	@UiField OneWidgetLayoutPanel chartPanel; 
	
  public void onModuleLoad() {
	  DockLayoutPanel outer = binder.createAndBindUi(this);
	  

	  
	  ClientFactory clientFactory = GWT.create(ClientFactory.class);
	  EventBus eventBus = clientFactory.getEventBus();
	  PlaceController placeController = clientFactory.getPlaceController();

	  final MyRequestFactory rf = clientFactory.getRequestFactory();
	  /*StockRequest sr = rf.stockRequest();
	  StockProxy stock = sr.create(StockProxy.class);
	  stock.setName("BB");
	  sr.save(stock).fire();*/
	  
	  /*rf.stockRequest().getAllWithChildren().with("quote").fire(new Receiver<List<StockProxy>>(){
		@Override
		public void onSuccess(List<StockProxy> response) {
			StockProxy sp = response.get(0);
			long id = sp.getId();
			QuoteProxy qt = sp.getQuote();
			long idd = qt.getSymbol();
		}});*/
	  
	  /*QuoteRequest qr = rf.quoteRequest();
	  QuoteProxy q = qr.create(QuoteProxy.class);
	  q.setSymbol(new Long(56));
	  q.setClose(1.2945);
	  q.setDate(new Date());
	  qr.save(q).fire();*/
	  
	  
	  /*UserListRequest ur = rf.userListRequest();
	  UserListProxy ulist = ur.create(UserListProxy.class);
	  ulist.setName("Listaa3");
	  ur.save(ulist).fire();*/
	  
	  /*UserListRequest tr = rf.userListRequest();
	  UserListProxy tp = tr.create(UserListProxy.class);
	  tp.setStocks(new ArrayList<StockProxy>());
	  tr.edit(stock);
	  tp.getStocks().add(stock);
	  tr.save(tp).with("stocks").fire();*/
	  
	
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
