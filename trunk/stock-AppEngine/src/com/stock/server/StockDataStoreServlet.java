package com.stock.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.Entity;
import com.stock.server.entities.Quote;
import com.stock.server.entities.Stock;

@SuppressWarnings("serial")
public class StockDataStoreServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		QuoteDao quoteDao = new QuoteDao();
		StockDao dao = new StockDao();
		//Stock st = new Stock();
		//st.setName("wig20");
		//dao.save(st);
		List<Stock> stocks = dao.getAll();
		String tmp = "";
		for(Stock s:stocks){
			tmp+=s.getName().toLowerCase()+"+";
		}
		tmp = tmp.substring(0, tmp.length()-1);
		
		URL stooq = new URL("http://stooq.pl/q/l/?s="+tmp+"&f=sd2t2ohlcv&h&e=csv");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stooq.openStream()));
		String inputLine = reader.readLine();
		while ((inputLine = reader.readLine()) != null){
			StringTokenizer tokenizer = new StringTokenizer(inputLine, ",");
			int len = 8;
			String [] quote = new String[len];
		    int i = 0;
		    while(tokenizer.hasMoreTokens()){
		    	quote[i] = tokenizer.nextToken();
		    	i++;
		    }
		    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM,new Locale("PL"));
		    Date date = null;
		    try {
				date = df.parse(quote[1]+" "+quote[2]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    Quote qt= new Quote(); 
		    qt.setDate(date);
		    qt.setOpen(Double.valueOf(quote[3]));
		    qt.setHigh(Double.valueOf(quote[4]));
		    qt.setLow(Double.valueOf(quote[5]));
		    qt.setClose(Double.valueOf(quote[6]));
		    qt.setVolume(Integer.valueOf(quote[7]));
		    for(Stock s:stocks){
		    	if(s.getName().toLowerCase().equals(quote[0].toLowerCase())){
		    		qt.setSymbol(s.getId());
		    		break;
		    	}
		    }
		    quoteDao.save(qt);
		}
			  
		
		reader.close();
	}
}
