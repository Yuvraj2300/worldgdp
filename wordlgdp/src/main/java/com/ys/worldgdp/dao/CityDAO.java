package com.ys.worldgdp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.ys.worldgdp.City;
import com.ys.worldgdp.dao.mapper.CityRowMapper;

import lombok.Setter;

@Service
@Setter
public class CityDAO {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final Integer PAGE_SIZE = 20;

	//OVERLOADED METHOD TO HANDLE PAGE NO NULL
	public List<City> getCities(String countryCode) {
		return getCities(countryCode, null);
	}
	
	public List<City> getCities(String countryCode, Integer pageNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("code", countryCode);
		if (pageNo != null) {
			Integer offset = (pageNo - 1) * PAGE_SIZE;
			params.put("offset", offset);
			params.put("size", PAGE_SIZE);
		}
		
		return namedParameterJdbcTemplate.query("SELECT"
				+ "ID,NAME,COUNTRYCODE COUNTRY_CODE,DISTRICT,POPULATION "
				+ "FROM CITY WHERE COUNTRYCODE = :code"
				+ "ORDER BY POPULATION DESC"
				+ ((pageNo != null) ? "LIMIT:offset, :size" : ""),
			params, new CityRowMapper());
	}
	
	public City getCityDetail(Long cityId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", cityId);
		
		return namedParameterJdbcTemplate.queryForObject("SELECT id,"
				+ "name,countrycode country_code,"
				+ "district,population"
				+ "from city where id = :id", 
			params, new CityRowMapper());
	}
	
	public long addCity(String countryCode, City city) {
		SqlParameterSource paramSource = 
				new MapSqlParameterSource(getMapForCity(countryCode, city));
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("INSERT INTO city("
				+ " name, countrycode, "
				+ " district, population) "
				+ " VALUES (:name, :country_code, "
				+ " :district, :population )",
				paramSource, keyHolder);
		return keyHolder.getKey().longValue();
		}
	
	private Map<String, Object> getMapForCity(String countryCode, City city) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", city.getName());
		map.put("country_code", city.getCountryCode());
		map.put("district", city.getDistrict());
		map.put("population", city.getPopulation());
		return map;
	}

	public void deleteCity(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		namedParameterJdbcTemplate.update("DELETE FROM city WHERE id = :id", params);
	}
}
