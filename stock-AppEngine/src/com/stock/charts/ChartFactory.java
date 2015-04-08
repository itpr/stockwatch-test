package com.stock.charts;

import java.io.Serializable;
import java.util.List;

import com.stock.shared.QuoteProxy;
import com.stock.shared.charts.AbstractChart;
import com.stock.shared.charts.AbstractIndicator;
import com.stock.shared.charts.IndicatorsEnum;
import com.stock.shared.charts.TimePeriod;

public class ChartFactory implements Serializable{

	private static final long serialVersionUID = 1096696958732527450L;
	
	public static AbstractIndicator buildIndicator(IndicatorsEnum indicatorsEnum, TimePeriod timePeriod, List<QuoteProxy> quotes){
		switch (indicatorsEnum) {
			case RSI:
				return new RSI(indicatorsEnum, timePeriod, quotes);
			case ROC:
				return new ROC(indicatorsEnum, timePeriod, quotes);
			case EMA:
				return new EMA(indicatorsEnum, timePeriod, quotes);
			case RWILLIAMS:
				return new RWilliams(indicatorsEnum, timePeriod, quotes);
		}
		
		return null;
	}
	
	public static AbstractChart buildChart(TimePeriod timePeriod, List<QuoteProxy> quotes){
		return new MainChart(timePeriod, quotes);
	}

}
