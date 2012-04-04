package com.stock.client.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.stock.server.entities.Stock;
import com.stock.shared.QuoteProxy;
import com.stock.shared.StockProxy;

public class StockCell extends AbstractCell<StockProxy> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			StockProxy value, SafeHtmlBuilder sb) {
		
	      if (value == null) {
	          return;
	        }
	      QuoteProxy qt = value.getQuote();
	      sb.appendHtmlConstant("<table><tr><td>");
	      sb.appendEscaped(value.getName());
	      sb.appendHtmlConstant("</td></tr><tr><td style='font-size:80%;'>");
	      if(qt!=null){
		      sb.appendEscaped(qt.getClose().toString());
		      sb.appendHtmlConstant("</td><td style='font-size:60%;text-align: right;width: 150px;'>");
		      sb.appendEscaped(qt.getDate().toGMTString());
	      }
	      sb.appendHtmlConstant("</td></tr></table>");
	}

}
