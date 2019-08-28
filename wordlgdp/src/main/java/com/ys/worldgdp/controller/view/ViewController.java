package com.ys.worldgdp.controller.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ys.worldgdp.dao.CityDAO;
import com.ys.worldgdp.dao.CountryDAO;
import com.ys.worldgdp.dao.LookupDAO;

@Controller
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
		model.addAttribute("continents", lookupDAO.getContinents());
		model.addAttribute("region", lookupDAO.getRegions());
		model.addAttribute("countries", countryDAO.getCountries(params));
		model.addAttribute("count", countryDAO.getCountriesCount(params));
		return "countries";
	}
	
	@GetMapping("/countries/{countryCode}")
	public	String	getCountryDetail(@PathVariable	String	countryCode,
			Model	model) {
		model.addAttribute("c", countryDAO.getCountryDetail(countryCode));
		return	"country";
	}
	
	@GetMapping("/countries/{countryCode}/form")
	public String editCountry(@PathVariable String countryCode,
			Model model) {
		model.addAttribute("c", countryDAO.getCountryDetail(countryCode));
		model.addAttribute("cities", cityDAO.getCities(countryCode));
		model.addAttribute("continents", lookupDAO.getContinents());
		model.addAttribute("regions", lookupDAO.getRegions());
		model.addAttribute("heads", lookupDAO.getHeadOfStates());
		model.addAttribute("govs", lookupDAO.getGovernmentTypes());
		return "country-form";
	}

}
