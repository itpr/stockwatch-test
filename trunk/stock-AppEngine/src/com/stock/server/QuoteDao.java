package com.stock.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;
import com.stock.server.entities.Quote;
import com.stock.server.entities.Stock;
import com.stock.shared.charts.TimePeriod;

public class QuoteDao extends DAOBase
{
	private static String QUOTE_RGX = "(\\d+),(\\d+\\.\\d+),(\\d+\\.\\d+),(\\d+\\.\\d+),(\\d+\\.\\d+),(\\d+)";
	
	static{
		ObjectifyService.register(Quote.class);
		ObjectifyService.register(Stock.class);	
	}

	public void save(Quote entity){
		Objectify ofy = ofy();
		Quote qt = ofy.query(Quote.class).filter("symbol", entity.getSymbol()).filter("date", entity.getDate()).get();
		if(qt==null){
			ofy.put(entity);
		}
	}
	
	public Quote getLatestQuote(Long id){
		List<Quote> quotes = getAllById(id, null);
		if(quotes.size()<=0){
			return null;
		}
		Collections.sort(quotes);
		return quotes.get(0);
	}
	
	public List<Quote> getAllById(Long id, TimePeriod timePeriod){
		List<Quote> quotes = new ArrayList<Quote>();
		if(timePeriod != null){
			switch (timePeriod){
				case ONE_DAY : 
					quotes = getAllDailyById(id);
					break;
				case ONE_MINUTE: 
					quotes = getAllMinuteById(id);
					break;
			}
		}else{
			quotes = getAllDailyById(id);
		}
		return quotes;
	}
	
	public List<Quote> getAllDailyById(Long id){
		Objectify ofy = ofy();
		Stock stock = ofy.query(Stock.class).filter("id", id.longValue()).get();
		List<Quote> quotes = new ArrayList<Quote>();
		
		URL url;
		try {
			url = new URL("http://chartapi.finance.yahoo.com/instrument/1.0/"+ stock.getShortName() +"/chartdata;type=quote;range=1y/csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine = null;
			while ((inputLine = reader.readLine()) != null){
			
				Matcher matcher = Pattern.compile(QUOTE_RGX).matcher(inputLine);
				if(matcher.find()){
					Quote quote = new Quote();
					String dateString = matcher.group(1);
					Date date = new SimpleDateFormat("yyyyMMdd").parse(dateString);
					quote.setDate(date);
					quote.setClose(Double.parseDouble(matcher.group(2)));
					quote.setHigh(Double.parseDouble(matcher.group(3)));
					quote.setLow(Double.parseDouble(matcher.group(4)));
					quote.setOpen(Double.parseDouble(matcher.group(5)));
					quote.setVolume(Integer.parseInt(matcher.group(6)));
					quotes.add(quote);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quotes;
	}
	
	public List<Quote> getAllMinuteById(Long id){
		Objectify ofy = ofy();
		Stock stock = ofy.query(Stock.class).filter("id", id.longValue()).get();
		List<Quote> quotes = new ArrayList<Quote>();
		
		try {
			URL url = new URL("http://chartapi.finance.yahoo.com/instrument/1.0/"+ stock.getShortName() +"/chartdata;type=quote;range=2d/csv");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine = null;
			while ((inputLine = reader.readLine()) != null){
			
				Matcher matcher = Pattern.compile(QUOTE_RGX).matcher(inputLine);
				if(matcher.find()){
					Quote quote = new Quote();
					quote.setDate(new Date(Long.parseLong(matcher.group(1)+"000")));
					quote.setClose(Double.parseDouble(matcher.group(2)));
					quote.setHigh(Double.parseDouble(matcher.group(3)));
					quote.setLow(Double.parseDouble(matcher.group(4)));
					quote.setOpen(Double.parseDouble(matcher.group(5)));
					quote.setVolume(Integer.parseInt(matcher.group(6)));
					quotes.add(quote);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quotes;
	}
	
	public Key<Stock> getStock(Integer id){
		return ofy().query(Stock.class).filter("id", id.longValue()).getKey();
	}

}
