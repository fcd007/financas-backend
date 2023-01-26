package br.com.code7.financasbackend.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.postgresql.Driver");
		dataSourceBuilder.url("jdbc:postgresql://localhost:5432/finance_db_v1");
		dataSourceBuilder.username("postgres");
		dataSourceBuilder.password("1q2w3e4r@#$");
		return dataSourceBuilder.build();
	}
}
