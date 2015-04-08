
package com.stock.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


public class ChartPlace extends Place {
  
	private String name;

	public ChartPlace(String token) {
		this.name = token;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<ChartPlace> {

		@Override
		public String getToken(ChartPlace place) {
			return place.getName();
		}

		@Override
		public ChartPlace getPlace(String token) {
			return new ChartPlace(token);
		}

	}
}
