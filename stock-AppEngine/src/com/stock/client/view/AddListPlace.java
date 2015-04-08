
package com.stock.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


public class AddListPlace extends Place {
  
	
	private String name;

	public AddListPlace(String token) {
		this.name = token;
	}

	public String getName() {
		return name;
	}

	
	public static class Tokenizer implements PlaceTokenizer<AddListPlace> {

		@Override
		public String getToken(AddListPlace place) {
			return place.getName();
		}

		@Override
		public AddListPlace getPlace(String token) {
			return new AddListPlace(token);
		}

	}
}
