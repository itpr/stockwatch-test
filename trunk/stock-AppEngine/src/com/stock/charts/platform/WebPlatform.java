package com.stock.charts.platform;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.stock.shared.platform.AbstractPlatform;

public class WebPlatform extends AbstractPlatform {

	
	private static final long serialVersionUID = -9142658127120602253L;
	
	private AbstractDataTable data;
	
	private Options options;
	
	public WebPlatform(AbstractDataTable data){
		this.data = data;
		this.options = Options.create();
	}

	public AbstractDataTable getData() {
		return data;
	}

	public void setData(AbstractDataTable data) {
		this.data = data;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}
	

}
