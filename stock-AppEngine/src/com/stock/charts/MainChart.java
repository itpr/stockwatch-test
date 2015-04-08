package com.stock.charts;

import java.util.List;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.stock.charts.platform.WebPlatform;
import com.stock.shared.QuoteProxy;
import com.stock.shared.charts.AbstractChart;
import com.stock.shared.charts.TimePeriod;

public class MainChart extends AbstractChart<WebPlatform>{

	private static final long serialVersionUID = 6648284819056926160L;


	public MainChart(TimePeriod timePeriod ,List<QuoteProxy> quotes) {
		super(quotes, timePeriod);
	}

	@Override
	public WebPlatform getPlatformData() {
		return new WebPlatform(createDataTable());
	}

	private AbstractDataTable createDataTable(){
					
	    DataTable data = DataTable.create();
	    data.addColumn(ColumnType.DATETIME, "Time");
	    data.addColumn(ColumnType.NUMBER, "Value");
	    data.addRows(quotes.size());
	    for(int i=0;i<timePeriod.getTicks();i++){
	    	data.setValue(i, 0, quotes.get(quotes.size()-timePeriod.getTicks() + i).getDate());
	    	data.setValue(i, 1, quotes.get(quotes.size()-timePeriod.getTicks() + i).getClose());
	    }
	    return data;
	}

}
