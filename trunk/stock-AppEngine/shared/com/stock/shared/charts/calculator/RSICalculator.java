package com.stock.shared.charts.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.stock.shared.QuoteProxy;

public class RSICalculator implements Serializable{

	private static final long serialVersionUID = 6182043357011612674L;

	
	public static List<Double> calculate(List<QuoteProxy> quotes, int period, int ticks){
		List<Double> values = new ArrayList<Double>(period);
		for(int i = 0; i < ticks; i++){
			values.add(calculateValue(quotes.subList(i, period+i)));
		}
		return values;
	}
	
	public static Double calculateValue(List<QuoteProxy> quotes){
		
        int lastBar = quotes.size() - 1;   
        int firstBar = 0;   
        
        double aveGain = 0, aveLoss = 0;   
        for (int bar = firstBar + 1; bar <= lastBar; bar++) {   
            double change = quotes.get(bar).getClose() - quotes.get(bar - 1).getClose();   
            if (change >= 0) {   
                aveGain += change;   
            } else {   
                aveLoss += change;   
            }   
        }   
   
        double rs = aveGain / Math.abs(aveLoss);   
        double rsi = 100 - 100 / (1 + rs);   
   
        return rsi;   
	}

}
