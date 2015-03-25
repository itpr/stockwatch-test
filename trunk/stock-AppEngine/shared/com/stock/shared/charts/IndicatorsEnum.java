package com.stock.shared.charts;

public enum IndicatorsEnum {
	
	RSI("RSI", 14),
	EMA("EMA", 30),
	ROC("ROC", 14),
	RWILLIAMS("RWILLIAMS", 14);
	

	private IndicatorsEnum(String name, int period) {
		this.name = name;
		this.period = period;
	}

	private String name;
	private int period;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	
}
