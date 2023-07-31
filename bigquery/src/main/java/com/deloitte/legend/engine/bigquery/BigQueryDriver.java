package com.deloitte.legend.engine.bigquery;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class BigQueryDriver implements Driver {

	static {
	    try {
	      register();
	    } catch (SQLException e) {
	      java.sql.DriverManager.println("Registering driver failed: " + e.getMessage());
	    }
	  }
	
	private static BigQueryDriver registeredDriver;
	
	static void register() throws SQLException {
	    if (isRegistered()) {
	      throw new IllegalStateException(
	          "Driver is already registered. It can only be registered once.");
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
	    //throw JdbcSqlExceptionFactory.of("The driver has not been registered", Code.FAILED_PRECONDITION);
		
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
	
	public BigQueryDriver() {}
	
	public Connection connect(String url, Properties info) throws SQLException {
		return new BigQueryConnection(projectId, datasetId);
	}

	public boolean acceptsURL(String url) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
