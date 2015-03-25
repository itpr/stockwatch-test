package com.stock.shared.charts;

public enum TimePeriod {
	ONE_MINUTE("1 minute", 50),
	ONE_DAY("1 day", 50);
	
	private String name;
	private Integer ticks;
	
	TimePeriod(String name, Integer ticks){
		this.name = name;
		this.ticks = ticks;
	}

	public String getName() {
		return name;
	}

	public Integer getTicks() {
		return ticks;
	}
}
