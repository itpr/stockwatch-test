
package com.stock.client.view;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.stock.shared.StockProxy;


public interface StocksListView extends IsWidget {

	void setPresenter(Presenter listener);
	
	void setStocksData(List<StockProxy> stocks);

	public interface Presenter {
		
		void goTo(Place place);
		
		void onNextButtonClick();

		void onPrevButtonClick();
		
		void onCellSelected(long id);
	}

	void setListName(String txt);

	void unselect();
}
