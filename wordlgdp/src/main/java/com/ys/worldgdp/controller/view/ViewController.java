package com.ys.worldgdp.controller.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ys.worldgdp.dao.CityDAO;
import com.ys.worldgdp.dao.CountryDAO;
import com.ys.worldgdp.dao.LookupDAO;

@RestController
@RequestMapping("/")
public class ViewController {

	@Autowired
	private CountryDAO countryDAO;

	@Autowired
	private LookupDAO lookupDAO;

	@Autowired
	private CityDAO cityDAO;

	@GetMapping({ "/countries", "/" })
	public String countries(Model	model,
			@RequestParam	Map<String,Object>	params) {
		return "countries";
	}
	
	@GetMapping("/countries/{countryCode}")
	public	String	getCountryDetail(@PathVariable	String	countryCode,
			Model	model) {
		return	"country";
	}
	
	@GetMapping("/countries/{countryCode}/form")
	public	String	editCountry(@PathVariable	String	countryCode,
			Model	model) {
		return	"country-form";
	}
	
}
