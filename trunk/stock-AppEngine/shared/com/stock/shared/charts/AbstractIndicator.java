package com.stock.shared.charts;

import java.util.List;

import com.stock.shared.QuoteProxy;
import com.stock.shared.platform.AbstractPlatform;


abstract public class AbstractIndicator<T extends AbstractPlatform> extends AbstractChart<T>{

	private static final long serialVersionUID = 2743395133394973678L;
	protected IndicatorsEnum indicatorsEnum;
	
	public AbstractIndicator(IndicatorsEnum indicatorsEnum, TimePeriod timePeriod, List<QuoteProxy> quotes){
		super(quotes, timePeriod);
		this.indicatorsEnum = indicatorsEnum;
	}
	

}
