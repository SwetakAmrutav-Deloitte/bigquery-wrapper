package com.deloitte.legend.engine.bigquery;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigQueryDriver implements Driver {
	
	static {
		try {
			register();
		} catch (SQLException e) {
			java.sql.DriverManager.println("Registering driver failed: " + e.getMessage());
		}
	}
	
	private static final String BIGQUERY_URI_FORMAT = "jdbc:bigquery:(?<HOSTGROUP>https?://[\\w.-]+(?:\\.[\\w.-]+)*(:\\d+)?(?:/.*)?);ProjectId=(?<ProjectId>[a-zA-Z0-9-]+);DefaultDataset=(?<DefaultDataset>[a-zA-Z0-9]+)(?:;.*)?";
	private static final Pattern URL_PATTERN = Pattern.compile(BIGQUERY_URI_FORMAT);

	private static BigQueryDriver registeredDriver;

	static void register() throws SQLException {
		if (isRegistered()) {
			throw new IllegalStateException("Driver is already registered. It can only be registered once.");
		}
		BigQueryDriver registeredDriver = new BigQueryDriver();
		DriverManager.registerDriver(registeredDriver);
		BigQueryDriver.registeredDriver = registeredDriver;
	}

	static boolean isRegistered() {
		return registeredDriver != null;
	}

	static BigQueryDriver getRegisteredDriver() throws SQLException {
		if (isRegistered()) {
			return registeredDriver;
		}
		throw new SQLException("The driver has not been registered");
	}

	private String projectId;
	private String datasetId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public BigQueryDriver() {
	}

	public Connection connect(String url, Properties info) throws SQLException {
		String projectId = info.getProperty("bigquery_projectId");
		String datasetId = info.getProperty("bigquery_defaultDataset");
		if (url != null && url.startsWith("jdbc:bigquery")) {
			try {
				Matcher matcher = URL_PATTERN.matcher(url);
				if (matcher.matches()) {
					// Connection connection = new BigQueryConnection(projectId, datasetId);
					System.out.println("Matching URL");
				}
			} catch (Exception e) {
				System.out.println("Invalid URL: " + e.getMessage());
			}

			System.out.println("Project ID from Properties: " + projectId);
			System.out.println("Dataset ID from Properties: " + datasetId);
			
			//info.forEach((key, val) -> System.out.println(key + " = " + val));

			return new BigQueryConnection(projectId, datasetId);
		}
		return new BigQueryConnection(projectId, datasetId);
	}

	public boolean acceptsURL(String url) throws SQLException {
		//return URL_PATTERN.matcher(url).matches();
		return true;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMajorVersion() {
		return 0;
	}

	public int getMinorVersion() {
		return 0;
	}

	public boolean jdbcCompliant() {
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

}
