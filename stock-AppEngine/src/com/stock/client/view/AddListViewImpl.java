
package com.stock.client.view;

import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.stock.shared.StockProxy;


public class AddListViewImpl extends Composite implements AddListView {

	interface Binder extends UiBinder<Widget, AddListViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField
	VerticalPanel verticalPanel;
	
	@UiField
	TextBox textBox;
	
	@UiField(provided=true)
	CellList<StockProxy> cellList;
	
	@UiField
	Button addButton;

	private Presenter listener;

	private LabelCheckCell cell;
	
	public AddListViewImpl() {
		cell = new LabelCheckCell();
		cellList = new CellList<StockProxy>(cell);
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	
	@Override
	public void setData(List<StockProxy> list) {
		cellList.setRowData(0,list);
	}

	@Override
	public Set<StockProxy> getSelectedFeatures() {
		return cell.featuresSet;
	}
	
	@Override
	public String getTextBox(){
		return textBox.getValue();
	}
	
	@UiHandler("addButton")
	public void onAddButtonClick(ClickEvent evt){
		listener.onAddButtonClick();
	}
}
