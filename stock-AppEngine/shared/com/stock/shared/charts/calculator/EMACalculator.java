package com.stock.shared.charts.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.stock.shared.QuoteProxy;

public class EMACalculator implements Serializable{

	private static final long serialVersionUID = 6182043357011612674L;

	
	public static List<Double> calculate(List<QuoteProxy> quotes, int period, int ticks){
		List<Double> values = new ArrayList<Double>(period);
		for(int i = 0; i < ticks; i++){
			values.add(calculateValue(quotes.subList(i, period+i)));
		}
		return values;
	}
	
	public static Double calculateValue(List<QuoteProxy> quotes){
		
        int startBar = 0;  
        int endBar = quotes.size() -1;
        double multiplier = 2. / (quotes.size() + 1.);
        double ema = quotes.get(startBar).getClose();   
   
        for (int bar = startBar; bar <= endBar; bar++) {   
            double barClose = quotes.get(bar).getClose();   
            ema += (barClose - ema) * multiplier;   
        }   
   
        return ema; 
	}

}
