package com.stock.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.stock.shared.charts.IndicatorsEnum;
import com.stock.shared.charts.TimePeriod;


public interface ChartView extends IsWidget {
  

	void setPresenter(Presenter listener);

	public interface Presenter {
		
		void goTo(Place place);
		
		void indicatorAdded(IndicatorsEnum indicatorsEnum);
		
		void timePeriodSelected(TimePeriod timePeriod);
	}

	void addIndicatorChart(LineChart ch);
	
	void addChart(LineChart ch);
	
	void clearChartPanel();
}
