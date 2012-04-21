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

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.stock.server.entities.Stock;
import com.stock.shared.StockProxy;

/**
 * Sample implementation of {@link StocksListView}.
 */
public class StocksListViewImpl extends Composite implements StocksListView {

	interface Binder extends UiBinder<Widget, StocksListViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	private StockProxy selected;
	private final SingleSelectionModel<StockProxy> selectionModel;
	private Presenter listener;
	
	@UiField(provided = true)
	CellList<StockProxy> stocksList;
	
	@UiField
	Button prevButton;
	
	@UiField
	Button nextButton;
	
	@UiField
	Label listName;

	public StocksListViewImpl() {
	    StockCell stockCell = new StockCell();
	    stocksList = new CellList<StockProxy>(stockCell);
		
	    
	    selectionModel = new SingleSelectionModel<StockProxy>();
	    stocksList.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	        selected = selectionModel.getSelectedObject();
	        if (selected != null) {
	        	listener.onCellSelected(selected.getId());
	        }
	        
	      }
	    });

	    
	    
		initWidget(binder.createAndBindUi(this));
	}


	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}


	@Override
	public void setStocksData(List<StockProxy> stocks) {
		stocksList.setRowData(stocks);
	}
	
	@Override
	public void setListName(String txt){
		listName.setText(txt);
	}
	
	@Override
	public void unselect(){
		selectionModel.setSelected(selected, false);
	}
	
	@UiHandler("nextButton")
	public void onNextButtonClick(ClickEvent evt){
		listener.onNextButtonClick();
	}

	@UiHandler("prevButton")
	public void onPrevButtonClick(ClickEvent evt){
		listener.onPrevButtonClick();
	}
}
