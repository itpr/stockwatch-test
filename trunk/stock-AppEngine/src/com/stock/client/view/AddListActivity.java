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

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class AddListActivity extends AbstractActivity implements AddListView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
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
