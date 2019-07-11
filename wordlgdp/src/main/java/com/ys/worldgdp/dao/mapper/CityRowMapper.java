package com.ys.worldgdp.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ys.worldgdp.City;

public class CityRowMapper implements RowMapper<City> {

	public City mapRow(ResultSet rs, int rowNum) throws SQLException {
		City	city	=	new	City();
		city.setCountryCode(rs.getString("country_code"));
		city.setName(rs.getString("name"));
		city.setId(rs.getLong("id"));
		city.setDistrict(rs.getString("district"));
		city.setPopulation(rs.getLong("population"));
		return city;
	}

}
