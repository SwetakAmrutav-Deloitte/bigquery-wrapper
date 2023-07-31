package com.deloitte.legend.engine.bigquery;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class BigQueryDataSource implements DataSource {

	// Make sure the JDBC driver class is loaded.
	static {
		try {
			Class.forName("org.finos.legend.engine.bigquery.BigQueryDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"BigQueryDataSource failed to load org.finos.legend.engine.bigquery.BigQueryDriver", e);
		}
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

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Connection getConnection() throws SQLException {
		return new BigQueryConnection(projectId, datasetId);
	}

	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}

	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
