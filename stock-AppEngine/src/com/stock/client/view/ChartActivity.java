package com.stock.client.view;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.stock.charts.ChartFactory;
import com.stock.charts.platform.WebPlatform;
import com.stock.client.ClientFactory;
import com.stock.shared.QuoteProxy;
import com.stock.shared.charts.AbstractChart;
import com.stock.shared.charts.AbstractIndicator;
import com.stock.shared.charts.IndicatorsEnum;
import com.stock.shared.charts.TimePeriod;


public class ChartActivity extends AbstractActivity implements ChartView.Presenter {


	private ClientFactory clientFactory;
	private ChartView view;
	private Long id;
	private TimePeriod timePeriod;
	private List<QuoteProxy> quotes;

	public ChartActivity(Place place, ClientFactory clientFactory) {
		
		this.id = Long.valueOf(((ChartPlace)place).getName());	
		this.clientFactory = clientFactory;
		this.timePeriod = TimePeriod.ONE_DAY;
	}

	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getChartView();
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());

        final Runnable onLoadCallback = new Runnable() {
            public void run() {
                populateQuotes();
            }
        };
        
        VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
		
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
	
	private void populateQuotes(){
		if(id > 0){
			view.clearChartPanel();
			
	    	clientFactory.getRequestFactory().quoteRequest().getAllById(id, timePeriod).fire(new Receiver<List<QuoteProxy>>(){
	
				@Override
				public void onSuccess(List<QuoteProxy> response) {
					quotes = response;
					
					AbstractChart mainChart = ChartFactory.buildChart(timePeriod, quotes);
					WebPlatform chartData = (WebPlatform) mainChart.getPlatformData();
					LineChart chart = new LineChart(chartData.getData(), chartData.getOptions());
					view.addChart(chart); 
					
				}});  	
		}
	}
	
	
	@Override
	public void indicatorAdded(IndicatorsEnum indicatorsEnum) {
		AbstractIndicator indicator = ChartFactory.buildIndicator(indicatorsEnum, timePeriod, quotes);
		if(indicator!=null){
			WebPlatform chartData = (WebPlatform) indicator.getPlatformData();
			LineChart indicatorChart = new LineChart(chartData.getData(), chartData.getOptions());
			view.addIndicatorChart(indicatorChart);
		}
	}

	@Override
	public void timePeriodSelected(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
		populateQuotes();
	}

}
