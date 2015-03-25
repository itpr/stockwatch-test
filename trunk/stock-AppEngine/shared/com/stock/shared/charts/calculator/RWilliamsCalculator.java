package com.stock.shared.charts.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.stock.shared.QuoteProxy;

public class RWilliamsCalculator implements Serializable{

	private static final long serialVersionUID = 6182043357011612674L;

	
	public static List<Double> calculate(List<QuoteProxy> quotes, int period, int ticks){
		List<Double> values = new ArrayList<Double>(period);
		for(int i = 0; i < ticks; i++){
			values.add(calculateValue(quotes.subList(i, period+i)));
		}
		return values;
	}
	
	public static Double calculateValue(List<QuoteProxy> quotes){
        
        double close = quotes.get(0).getClose();
        double max = getMaxValue(quotes);
        double min = getMinValue(quotes);
        double rW = (max - close)/(max - min) * (-100.0);
        
        return rW; 
	}

	private static double getMaxValue(List<QuoteProxy> quotes){
		
        double max = quotes.get(0).getHigh();
        
        for(QuoteProxy quote : quotes){
        	if(max < quote.getHigh()){
        		max = quote.getHigh();
        	}
        }
        
        return max; 
	}
	
	private static double getMinValue(List<QuoteProxy> quotes){
		
        double min = quotes.get(0).getLow();
        
        for(QuoteProxy quote : quotes){
        	if(min > quote.getLow()){
        		min = quote.getLow();
        	}
        }
        
        return min; 
	}
}
