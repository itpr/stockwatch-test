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

import java.util.Collections;
import java.util.List;

import com.stock.client.ClientFactory;
import com.stock.client.ClientFactoryImpl;
import com.stock.client.view.ChartPlace;
import com.stock.client.view.ChartView;
import com.stock.shared.QuoteProxy;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class ChartActivity extends AbstractActivity implements ChartView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	private ChartView view;
	private Long id;

	public ChartActivity(Place place, ClientFactory clientFactory) {
		
		this.id = Long.valueOf(((ChartPlace)place).getName());	
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getChartView();
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());

		if(id!=0){
	        Runnable onLoadCallback = new Runnable() {
	            public void run() {
	                
	            	clientFactory.getRequestFactory().stockRequest().getChildrens(id).fire(new Receiver<List<QuoteProxy>>(){
	
						@Override
						public void onSuccess(List<QuoteProxy> response) {
							
							LineChart chart = new LineChart(createTable(response), createOptions());
	            	
							view.addChart(chart);
							
						}});  	
	            }
	        };
	        
	        VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
		}
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
	
	private Options createOptions() {
	    Options options = Options.create();
	    //options.setTitle("WIG20");
	    return options;
	}
	
	
	private AbstractDataTable createTable(List<QuoteProxy> ll) {
			Collections.reverse(ll);
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.DATETIME, "Time");
		    data.addColumn(ColumnType.NUMBER, "Value");
		    data.addRows(ll.size());
		    for(int i=0;i<ll.size();i++){
		    	QuoteProxy qt= ll.get(i);
		    	data.setValue(i, 0, qt.getDate());
		    	data.setValue(i, 1, qt.getClose());
		    }
		    return data;
	}

}
