package com.stock.charts;

import java.util.List;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.stock.charts.platform.WebPlatform;
import com.stock.shared.QuoteProxy;
import com.stock.shared.charts.AbstractIndicator;
import com.stock.shared.charts.IndicatorsEnum;
import com.stock.shared.charts.TimePeriod;
import com.stock.shared.charts.calculator.RWilliamsCalculator;



public class RWilliams extends AbstractIndicator<WebPlatform>{

	private static final long serialVersionUID = 6648284819056926160L;

	public RWilliams(IndicatorsEnum indicatorsEnum, TimePeriod timePeriod, List<QuoteProxy> quotes) {
		super(indicatorsEnum, timePeriod, quotes);
	}

	@Override
	public WebPlatform getPlatformData() {
		return new WebPlatform(createDataTable());
	}
	
	private AbstractDataTable createDataTable(){

		List<Double> result = RWilliamsCalculator.calculate(quotes, indicatorsEnum.getPeriod(), timePeriod.getTicks());
					
	    DataTable data = DataTable.create();
	    data.addColumn(ColumnType.DATETIME, "Time");
	    data.addColumn(ColumnType.NUMBER, "Value");
	    data.addRows(result.size());
	    for(int i=0;i<timePeriod.getTicks();i++){
	    	data.setValue(i, 0, quotes.get(quotes.size()-timePeriod.getTicks() + i).getDate());
	    	data.setValue(i, 1, result.get(i));
	    }
	    return data;
	}
	
	
}
