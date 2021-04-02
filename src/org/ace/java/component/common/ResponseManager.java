package org.ace.java.component.common;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository("ResponseManager")
public class ResponseManager {
	Gson gson = new GsonBuilder().create();

	public String getResponseString(Object object) {
		String responseString = gson.toJson(object);
		return responseString;
	}
}
