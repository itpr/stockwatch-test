package com.stock.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stock.server.entities.Quote;
import com.stock.server.entities.Stock;


public class DailyStockDataStoreServlet extends HttpServlet {
        
        private static final long serialVersionUID = 7973217277613149944L;
        
        private static String QUOTE_RGX = "(\\d+),(\\d+\\.\\d+),(\\d+\\.\\d+),(\\d+\\.\\d+),(\\d+\\.\\d+),(\\d+)";

        public void doGet(HttpServletRequest req, HttpServletResponse resp)
                        throws IOException {
                resp.setContentType("text/plain");
                QuoteDao quoteDao = new QuoteDao();
                StockDao stockDao = new StockDao();
                
                URL url;
                try {
                        for(Stock stock : stockDao.getAll()){
                                url = new URL("http://chartapi.finance.yahoo.com/instrument/1.0/"+ stock.getShortName() +"/chartdata;type=quote;range=1y/csv");
        
                                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                                String inputLine = null;
								String lastLine = "";
                                while ((inputLine = reader.readLine()) != null){
									lastLine = inputLine;
                                }
								Matcher matcher = Pattern.compile(QUOTE_RGX).matcher(lastLine);
								if(matcher.find()){
										Quote quote = new Quote();
										String dateString = matcher.group(1);
										Date date = new SimpleDateFormat("yyyyMMdd").parse(dateString);
										quote.setDate(date);
										quote.setQuoteType(QuoteType.DAILY);
										quote.setSymbol(stock.getId());
										quote.setClose(Double.parseDouble(matcher.group(2)));
										quote.setHigh(Double.parseDouble(matcher.group(3)));
										quote.setLow(Double.parseDouble(matcher.group(4)));
										quote.setOpen(Double.parseDouble(matcher.group(5)));
										quote.setVolume(Integer.parseInt(matcher.group(6)));
										quoteDao.save(quote);
								}
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}