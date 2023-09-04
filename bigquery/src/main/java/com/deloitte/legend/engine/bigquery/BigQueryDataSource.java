/*
 * Copyright 2020 Google LLC
 */

package com.deloitte.legend.engine.bigquery;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class BigQueryDataSource implements DataSource 
{

    static
    {
        try 
        {
            Class.forName("com.deloitte.legend.engine.bigquery.BigQueryDriver");
        } 
        catch (ClassNotFoundException e) 
        {
            throw new IllegalStateException(
                    "BigQueryDataSource failed to load org.finos.legend.engine.bigquery.BigQueryDriver", e);
        }
    }

    private String projectId;
    private String datasetId;

    public String getProjectId() 
    {
        return projectId;
    }

    public void setProjectId(String projectId) 
    {
        this.projectId = projectId;
    }

    public String getDatasetId() 
    {
        return datasetId;
    }

    public void setDatasetId(String datasetId) 
    {
        this.datasetId = datasetId;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException 
    {
        throw new SQLFeatureNotSupportedException();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException 
    {
        throw new UnsupportedOperationException("Not Implemented");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException 
    {
        throw new UnsupportedOperationException("Not Implemented");
    }

    public Connection getConnection() throws SQLException 
    {
        return new BigQueryConnection(projectId, datasetId);
    }

    public Connection getConnection(String username, String password) throws SQLException 
    {
        return getConnection();
    }

    public PrintWriter getLogWriter() throws SQLException 
    {
        throw new UnsupportedOperationException("Not Implemented");
    }

    public void setLogWriter(PrintWriter out) throws SQLException 
    {
        throw new UnsupportedOperationException("Not Implemented");
    }

    public void setLoginTimeout(int seconds) throws SQLException 
    {
        throw new UnsupportedOperationException("Not Implemented");
    }

    public int getLoginTimeout() throws SQLException 
    {
        throw new UnsupportedOperationException("Not Implemented");
    }

}
