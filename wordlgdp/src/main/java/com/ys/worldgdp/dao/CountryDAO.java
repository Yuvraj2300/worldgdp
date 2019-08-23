package com.ys.worldgdp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ys.worldgdp.Country;
import com.ys.worldgdp.dao.mapper.CountryRowMapper;

import lombok.Setter;

@Service
@Setter
public class CountryDAO {
	
	@Autowired
	NamedParameterJdbcTemplate	namedParameterJdbcTemplate;
	
	private static final String SELECT_CLAUSE = "SELECT "
			+ "  	c.Code, "
			+ "		c.Name, "
			+ "		c.Continent, "
			+ "		c.region, "
			+ "		c.SurfaceArea surface_area, "
			+ "		c.IndepYear indep_year, "
			+ "		c.Population, "
			+ "		c.LifeExpectancy life_expectancy, "
			+ "		c.GNP, "
			+ "		c.LocalName local_name, "
			+ "		c.GovernmentForm government_form, "
			+ "		c.HeadOfState head_of_state, "
			+ "		c.code2 ,"
			+ "		c.capital ,"
			+ "		cy.name capital_name "
			+ " FROM country c"
			+ " LEFT OUTER JOIN city cy ON cy.id = c.capital ";
	
	private static final String SEARCH_WHERE_CLAUSE = " AND ( LOWER(c.name) "
			+ "	LIKE CONCAT('%', LOWER(:search), '%') ) ";
	
	private static final String CONTINENT_WHERE_CLAUSE = 
			" AND c.continent = :continent ";
	private static final String REGION_WHERE_CLAUSE = 
			" AND c.region = :region ";
	
	private static final String PAGINATION_CLAUSE = " ORDER BY c.code "
			+ "  LIMIT :size OFFSET :offset ";
	
	private static final Integer PAGE_SIZE = 20;
	
	public List<Country> getCountries(Map<String,Object>	params) {
		int	pageNo	=	1;
		//CHECKING FOR PAGE NO
		if (params.containsKey("pageNo")) {
			pageNo = Integer.parseInt(params.get("pageNo").toString());
		}
		
		Integer	 offset	=	(pageNo -1)*PAGE_SIZE;
		params.put("offset", offset);
		params.put("size",  PAGE_SIZE);
		
		System.out.println("Params: "+ params.toString());
		System.out.println(SELECT_CLAUSE.trim() + " WHERE 1 = 1 "
				+ (!StringUtils.isEmpty((String) params.get("search")) ? SEARCH_WHERE_CLAUSE.trim() : "")
				+ (!StringUtils.isEmpty((String) params.get("continent")) ? CONTINENT_WHERE_CLAUSE.trim() : "")
				+ (!StringUtils.isEmpty((String) params.get("region")) ? REGION_WHERE_CLAUSE.trim() : "") + " ORDER BY c.code "
				+ "  LIMIT :size OFFSET :offset ");
		
		return namedParameterJdbcTemplate.query(
				SELECT_CLAUSE + "WHERE 1=1 "
						+ (StringUtils.isEmpty((String) params.get("search")) ? SEARCH_WHERE_CLAUSE : "")
						+ (StringUtils.isEmpty((String) params.get("continent")) ? CONTINENT_WHERE_CLAUSE : "")
						+ (StringUtils.isEmpty(params.get("region")) ? REGION_WHERE_CLAUSE : "") + PAGINATION_CLAUSE,
				params, new CountryRowMapper());
	}
	
	public int getCountriesCount(Map<String, Object> params) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM COUNTRY c" + "WHERE 1=1"
						+ (StringUtils.isEmpty((String) params.get("search")) ? SEARCH_WHERE_CLAUSE : "")
						+ (StringUtils.isEmpty((String) params.get("continent")) ? CONTINENT_WHERE_CLAUSE : "")
						+ (StringUtils.isEmpty(params.get("region")) ? REGION_WHERE_CLAUSE : ""),
				params, Integer.class);
	}

	public Country getCountryDetail(String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		return namedParameterJdbcTemplate.queryForObject(SELECT_CLAUSE + "WHERE c = :code", params,
				new CountryRowMapper());
	}

	public void editCountryDetail(String code, Country country) {
			namedParameterJdbcTemplate.update(" UPDATE country SET "
			+ " name = :name, "
			+ " localname = :localName, "
			+ " capital = :capital, "
			+ " continent = :continent, "
			+ " region = :region, "
			+ " HeadOfState = :headOfState, "
			+ " GovernmentForm = :governmentForm, "
			+ " IndepYear = :indepYear, "
			+ " SurfaceArea = :surfaceArea, "
			+ " population = :population, "
			+ " LifeExpectancy = :lifeExpectancy "
			+ "WHERE Code = :code ",
		getCountryAsMap(code, country));
	}
	
	public Map<String, Object> getCountryAsMap(String code, Country country) {
		Map<String, Object> countryMap = new HashMap<>();
		countryMap.put("name", country.getName());
		countryMap.put("localName", country.getLocalName());
		countryMap.put("capital", country.getCapital());
		countryMap.put("continent", country.getContinent());
		countryMap.put("region", country.getRegion());
		countryMap.put("headOfState", country.getHeadOfState());
		countryMap.put("governmentForm", country.getGovernmentForm());
		countryMap.put("indepYear", country.getIndepYear());
		countryMap.put("surfaceArea", country.getSurfaceArea());
		countryMap.put("population", country.getPopulation());
		countryMap.put("lifeExpectancy", country.getLifeExpectancy());
		countryMap.put("code", code);
		return countryMap;
	}
}
