package com.ys.worldgdp.external;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ys.worldgdp.CountryGDP;

@Service
public class WorldBankApiClient {
	String	GDP_URL="http://api.worldbank.org/countries/%s/indicators/NY.GDP.MKTP.CD?"
			+ "format=json&date=2007:2019";

	public List<CountryGDP> getGDP(String	countryCode) throws ParseException {
		RestTemplate worldBankRestTemplate = new RestTemplate();
		ResponseEntity<String>	response	=	
				worldBankRestTemplate.getForEntity(String.format(GDP_URL, countryCode),String.class);
		
		// To parse the response
		JSONParser parser = new JSONParser();
		JSONArray respData = (JSONArray) parser.parse(response.getBody());
		JSONArray data = (JSONArray) respData.get(1);

		List<CountryGDP>	listGDP	=	new ArrayList<CountryGDP>();
		JSONObject countryDataYearWise = null;

		for (int index = 0; index < data.size(); index++) {
			countryDataYearWise = (JSONObject) data.get(index);

			String valueStr = "0";
			if (countryDataYearWise.get("value") != null) {
				valueStr = countryDataYearWise.get("value").toString();
			}

			String yearStr = countryDataYearWise.get("year").toString();

			CountryGDP gdp = new CountryGDP();
			gdp.setValue((valueStr != null) ? Double.valueOf(valueStr) : null);
			gdp.setYear(Short.valueOf(yearStr));

			listGDP.add(gdp);
		}
		return listGDP;
	}
}
