package com.ys.worldgdp.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ys.worldgdp.CountryLanguage;

public class CountryLanguageRowMapper implements RowMapper<CountryLanguage> {

	public CountryLanguage mapRow(ResultSet rs, int rowNum) throws SQLException {
		CountryLanguage countrylng = new CountryLanguage();
		countrylng.setCountryCode(rs.getString("country_code"));
		countrylng.setIsOfficial(rs.getString("isofficial"));
		countrylng.setLanguage(rs.getString("language"));
		countrylng.setPercentage(rs.getDouble("percentage"));

		return countrylng;
	}

}
