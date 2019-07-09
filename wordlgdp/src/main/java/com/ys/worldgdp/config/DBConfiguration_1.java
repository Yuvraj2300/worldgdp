package com.ys.worldgdp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DBConfiguration_1 {
	// GETTING VALUES FROM APPLICATION PROPS FIL
	@Value("${jdbcUrl}")
	String jdbcUrl;
	@Value("${dataSource.user}")
	String username;
	@Value("${dataSource.password}")
	String password;
	@Value("${dataSourceClassName}")
	String className;

	public DataSource getDataSoruce() {
		// USING HIKARI FOR CONNECTION POOLING
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setDataSourceClassName(className);
		return ds;
	}

	// USING NAMED PARAMETER JDBC TEMPLATE
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		NamedParameterJdbcTemplate	namedParameterJdbcTemplate	=
				new	NamedParameterJdbcTemplate(getDataSoruce());
		return	namedParameterJdbcTemplate;
	}
}
