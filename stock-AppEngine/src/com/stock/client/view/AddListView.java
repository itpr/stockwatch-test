
package com.stock.client.view;

import java.util.List;
import java.util.Set;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.stock.shared.StockProxy;


public interface AddListView extends IsWidget {
  

	void setPresenter(Presenter listener);

	public interface Presenter {
		
		void goTo(Place place);
		
		void onAddButtonClick();
	}

	void setData(List<StockProxy> list);

	Set<StockProxy> getSelectedFeatures();

	String getTextBox();
}
