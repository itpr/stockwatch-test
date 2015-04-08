
package com.stock.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;


public interface ActionBarView extends IsWidget {
  
	void setName(String helloName);

	void setPresenter(Presenter listener);

	public interface Presenter {
		
		void goTo(Place place);
	}
}
