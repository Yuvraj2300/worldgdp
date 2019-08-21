package com.ys.worldgdp.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> getCountries(
			@RequestParam(name="search",required=false)String	search,
			@RequestParam(name="region",required=false)String	region,
			@RequestParam(name="continent",required=false)String	continent,
			@RequestParam(name="pageNo",required=false)Integer pageNo) {
		return	(ResponseEntity<?>) ResponseEntity.ok();
	}

	@PostMapping(value = "/{countryCode}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void editCountry() {

	}

	@GetMapping("/{countryCode}/gdp")
	public void getGDP() {

	}
}
