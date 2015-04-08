package com.stock.client.view;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class OneWidgetLayoutPanel extends SimplePanel implements AcceptsOneWidget {
	private IsWidget widget = null;
	
	@Override
	public void setWidget(IsWidget w) {
		
		if( widget != null) super.remove(widget);
		widget = w;
		
		if(w != null) super.add(w);
	}
}
