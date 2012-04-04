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

import com.stock.client.ClientFactory;
import com.stock.client.ClientFactoryImpl;
import com.stock.client.view.ChartPlace;
import com.stock.client.view.ChartView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class ChartActivity extends AbstractActivity implements ChartView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
	private String name;

	public ChartActivity(Place place, ClientFactory clientFactory) {
		
		this.name = ((ChartPlace)place).getName();	
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ChartView view = clientFactory.getChartView();
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
	}


	/**
	 * @see ChartView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
