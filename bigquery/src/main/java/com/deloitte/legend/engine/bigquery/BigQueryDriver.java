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

	private static final String BIGQUERY_URI_FORMAT = "(?:bigquery:)(?<HOSTGROUP>//[\\w.-]+(?:\\.[\\w\\.-]+)*[\\w\\-\\._~:/?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=.]+)?/projects/(?<PROJECTGROUP>(([a-z]|[-.:]|[0-9])+|(DEFAULT_PROJECT_ID)))(/instances/(?<INSTANCEGROUP>([a-z]|[-]|[0-9])+)(/databases/(?<DATABASEGROUP>([a-z]|[-]|[_]|[0-9])+))?)?(?:[?|;].*)?";

	private static final String BIGQUERY_URI_REGEX = "(?is)^" + BIGQUERY_URI_FORMAT + "$";
	private static final Pattern BIGQUERY_URI_PATTERN = Pattern.compile(BIGQUERY_URI_REGEX);

	private static final String JDBC_URL_FORMAT = "jdbc:" + BIGQUERY_URI_FORMAT;
	private static final Pattern URL_PATTERN = Pattern.compile(JDBC_URL_FORMAT);

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

	private boolean isValidUri(String uri) {
		return BIGQUERY_URI_PATTERN.matcher(uri).matches();
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
		System.out.println("Inside Wrapper Driver");
		String projectId = info.getProperty("PROJECT_ID");
		String datasetId = info.getProperty("PROJECT_ID");
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
			System.out.println("PROJECT_ID: " + info.getProperty("PROJECT_ID"));
			System.out.println("DATASET_ID: " + info.getProperty("DATASET_ID"));
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
