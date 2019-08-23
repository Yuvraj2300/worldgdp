package com.ys.worldgdp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
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

	//COMMENTING THIS PART TO USE H2 IN MEM DB FOR NOW.
/*	@Bean
	public DataSource getDataSoruce() {
		// USING HIKARI FOR CONNECTION POOLING
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setDataSourceClassName(className);
		return ds;
	}*/
	
	@Bean
	public DataSource getDataSource() {
		log.info("INITIAING H2 DATASOURCE");
		return new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				.ignoreFailedDrops(true)
				.addScript("classpath:h2_world.sql")
				.build();
	}

	// USING NAMED PARAMETER JDBC TEMPLATE
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		log.info("INITIATING NAMED JDBC PARAMETER");
		NamedParameterJdbcTemplate	namedParameterJdbcTemplate	=
				new	NamedParameterJdbcTemplate(getDataSource());
		return	namedParameterJdbcTemplate;
	}
}
