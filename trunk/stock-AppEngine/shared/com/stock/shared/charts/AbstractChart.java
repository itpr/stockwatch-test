package com.stock.shared.charts;

import java.io.Serializable;
import java.util.List;

import com.stock.shared.QuoteProxy;
import com.stock.shared.platform.AbstractPlatform;


abstract public class AbstractChart<T extends AbstractPlatform> implements Serializable{

	private static final long serialVersionUID = -3752895434681025793L;
	
	protected ChartType chartType;
	protected TimePeriod timePeriod;
	protected List<QuoteProxy> quotes;
	
	public AbstractChart(List<QuoteProxy> quotes, TimePeriod timePeriod){
		setChartType(ChartType.LINE_CHART);
		this.timePeriod = timePeriod;
		this.quotes = quotes;
	}
	
	public abstract T getPlatformData();
	
	public ChartType getChartType() {
		return chartType;
	}
	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}
	public TimePeriod getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	
}
