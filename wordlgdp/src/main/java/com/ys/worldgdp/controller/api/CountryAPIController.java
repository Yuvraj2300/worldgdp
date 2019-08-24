package com.ys.worldgdp.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ys.worldgdp.Country;
import com.ys.worldgdp.dao.CountryDAO;
import com.ys.worldgdp.external.WorldBankApiClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/countries")
@Slf4j
public class CountryAPIController {
	@Autowired
	CountryDAO countryDAO;

	@Autowired
	WorldBankApiClient apiClient;

	@GetMapping
	@ResponseBody
	public ResponseEntity<?> getCountries(
			@RequestParam(name="search",required=false)String	search,
			@RequestParam(name="region",required=false)String	region,
			@RequestParam(name="continent",required=false)String	continent,
			@RequestParam(name="pageNo",required=false)Integer pageNo) {
		
		try {
		Map<String, Object> params = new HashMap<>();
		params.put("search", search);
		params.put("region", region);
		params.put("continent", continent);
		if (pageNo != null) {
			params.put("pageNo", pageNo.toString());
		}

		List<Country> countries = countryDAO.getCountries(params);
		Map<String, Object> response = new HashMap<>();
		response.put("list", countries);
		response.put("count", countryDAO.getCountriesCount(params));

		return ResponseEntity.ok(response);
		} catch (Exception ex) {
			log.error("Error while fetching the country data.", ex.getMessage().toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while fetching the country data");
		}
	}

	@PostMapping(value = "/{countryCode}",
			consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> editCountry(@PathVariable	String	countryCode,
			@RequestBody	@Valid	Country country) {
		try {
		countryDAO.editCountryDetail(countryCode, country);
		Country	countryFromDB	=	countryDAO.getCountryDetail(countryCode);
		return	ResponseEntity.ok(countryFromDB);
		}catch(Exception ex) {
			log.error("");
			return	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while editing the country data");
		}
	}

	@GetMapping("/{countryCode}/gdp")
	public ResponseEntity<?> getGDP(@PathVariable String countryCode) {
		try {
			return ResponseEntity.ok(apiClient.getGDP(countryCode));
		} catch (Exception ex) {
			log.error("Error while getting the GDP");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while getting the GDP");
		}
	}
}
