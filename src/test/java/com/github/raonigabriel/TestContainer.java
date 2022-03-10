package com.github.raonigabriel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * @author raonigabriel
 *
 */
public final class TestContainer extends PostgreSQLContainer<TestContainer> {

	private final PGSimpleDataSource dataSource;

	public TestContainer(final DockerImageName dockerImageName) {
		super(dockerImageName);
		withDatabaseName("integration-tests-db").withUsername("sa").withPassword("sa");
		start();

		dataSource = new PGSimpleDataSource();
		dataSource.setUrl(getJdbcUrl());
		dataSource.setUser(getUsername());
		dataSource.setPassword(getPassword());
	}

	public DataSource getDataSource() {
		return dataSource;
	}


	public ResultSet executeQuery(final String sql) throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			return conn.createStatement().executeQuery(sql);
		}
	}

	public int executeUpdate(final String sql) throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			return conn.createStatement().executeUpdate(sql);
		}
	}

}
