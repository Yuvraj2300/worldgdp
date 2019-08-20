package com.ys.worldgdp.external;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ys.worldgdp.CountryGDP;

public class WorldBankApiClient {
	String	GDP_URL="http://api.worldbank.org/countries/%s/indicators/NY.GDP.MKTP.CD?"
			+ "format=json&date=2007:2019";

	public List<CountryGDP> getGDP(String	countryCode) {
		RestTemplate worldBankRestTemplate = new RestTemplate();
		ResponseEntity<String>	response	=	
				worldBankRestTemplate.getForEntity(String.format(GDP_URL, countryCode),String.class);
		//JsonP
		return null;
	}
}
