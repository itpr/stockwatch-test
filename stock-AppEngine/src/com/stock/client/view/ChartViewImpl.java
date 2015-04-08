package com.stock.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.stock.shared.charts.IndicatorsEnum;
import com.stock.shared.charts.TimePeriod;


public class ChartViewImpl extends Composite implements ChartView {

	interface Binder extends UiBinder<Widget, ChartViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	private LineChart indicatorChart;
	private LineChart chart;
	private Presenter listener;

	@UiField
	VerticalPanel panel;
	
	@UiField
	MenuBar menu;

	public ChartViewImpl() {
		initWidget(binder.createAndBindUi(this));
		createMenu();
	}


	
	private void createMenu(){
		MenuBar indicatorsMenu = new MenuBar(true);
		MenuBar timePeriodMenu = new MenuBar(true);
		
		for(IndicatorsEnum indicatorsEnum : IndicatorsEnum.values()){
			indicatorsMenu.addItem(indicatorsEnum.getName(), createIndicatorCommand(indicatorsEnum));
		}
		for(TimePeriod timePeriod : TimePeriod.values()){
			timePeriodMenu.addItem(timePeriod.getName(), createTimePeriodCommand(timePeriod));
		}
		
		menu.addItem("Add indicator", indicatorsMenu);
		menu.addItem("Select time period", timePeriodMenu);
		
	}
	
	private Command createIndicatorCommand(final IndicatorsEnum indicator){
		Command cmd = new Command() {
		    public void execute() {
		      listener.indicatorAdded(indicator);
		    }
		};
		return cmd;
	}
	
	private Command createTimePeriodCommand(final TimePeriod timePeriod){
		Command cmd = new Command() {
		    public void execute() {
		      listener.timePeriodSelected(timePeriod);
		    }
		};
		return cmd;
	}


	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	
	@Override
	public void addIndicatorChart(LineChart ch){
		if(indicatorChart!=null){
			panel.remove(indicatorChart);
		}
		indicatorChart = ch;
		panel.add(indicatorChart);
	}


	@Override
	public void addChart(LineChart ch) {
		if(chart!=null){
			panel.remove(chart);
		}
		chart = ch;
		panel.add(chart);
	}


	@Override
	public void clearChartPanel() {
		panel.clear();
	}

	

}
