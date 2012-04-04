package com.stock.client.view;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.stock.shared.StockProxy;

public class LabelCheckCell extends AbstractCell<StockProxy> {

	public static Set<StockProxy> featuresSet = new HashSet<StockProxy>();
	
	public LabelCheckCell(){
		super("change");
	}

	@Override
    public void onBrowserEvent(Context context, Element parent, StockProxy value,
        NativeEvent event, ValueUpdater<StockProxy> valueUpdater) {
    	  	
      if (value == null) {
        return;
      }

      super.onBrowserEvent(context, parent, value, event, valueUpdater);

      if ("change".equals(event.getType())) {
        updateChecked(parent, value);
      }
    }
    
    private InputElement getInputElement(Element parent) {
	      TableElement table = parent.getFirstChildElement().cast();
	      TableRowElement tr = table.getRows().getItem(0);
	      TableCellElement td = tr.getCells().getItem(0);
	      InputElement input = td.getFirstChildElement().cast();
	      return input;
	    }
	
	private void updateChecked(Element parent, StockProxy value) {
	      InputElement input = getInputElement(parent);

	      if (input.isChecked()) {
	        featuresSet.add(value);
	      } else {
	        featuresSet.remove(value);
	      }
		
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			StockProxy value, SafeHtmlBuilder sb) {
	      if (value == null) {
	          return;
	        }
	      
	      sb.appendHtmlConstant("<table><tr><td valign=\"top\">");
	      sb.appendHtmlConstant("<input type=\"checkbox\" />");
	      sb.appendHtmlConstant("</td><td>");
	      sb.appendHtmlConstant("<div style=\"padding-left:10px;\">");
	      sb.appendEscaped(value.getName());
	      sb.appendHtmlConstant("</div>");
	      sb.appendHtmlConstant("</td></tr></table>");
		
	}

}
