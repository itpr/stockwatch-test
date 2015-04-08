package com.stock.shared.charts.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.stock.shared.QuoteProxy;

public class ROCCalculator implements Serializable{

	private static final long serialVersionUID = 6182043357011612674L;

	
	public static List<Double> calculate(List<QuoteProxy> quotes, int period, int ticks){
		List<Double> values = new ArrayList<Double>(period);
		for(int i = 0; i < ticks; i++){
			values.add(calculateValue(quotes.subList(i, period+i)));
		}
		return values;
	}
	
	public static Double calculateValue(List<QuoteProxy> quotes){
	
   
        double priceNow = quotes.get(0).getClose();   
        double priceThen = quotes.get(quotes.size()-1).getClose();   
   
        double rateOfChange = ( (priceNow - priceThen) / priceThen) * 100;   
   
        return rateOfChange;  
	}

}
