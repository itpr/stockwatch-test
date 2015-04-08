package com.stock.server.entities;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.stock.server.QuoteType;


@Entity
public class Quote extends DatastoreObject implements Comparable<Quote>
{
	private Long symbol;
	private Date date;
	private Double open; 
	private Double high;
	private Double low;
	private Double close;
	private Integer volume;
	private QuoteType quoteType;

	public Quote(){
		
	}
	
	public Long getSymbol() {
		return symbol;
	} 

	public void setSymbol(Long nn) {
		this.symbol = nn;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	
	public QuoteType getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(QuoteType quoteType) {
		this.quoteType = quoteType;
	}

	@Override
	public int compareTo(Quote o) {
		return o.getDate().compareTo(this.getDate());
	}

}
