/*
 * Copyright 2020 Google LLC
 */

package com.deloitte.legend.engine.bigquery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

public class MainClass 
{

    private static final String PROJECT_ID = "crmsm-278014";
    private static final String DATASET_ID = "testlegendEngine";

    public static void main(String[] args) 
    {
        try 
        {
            Class.forName("com.deloitte.legend.engine.bigquery.BigQueryDriver");
            DataSource dataSource = new BigQueryDataSource();
            ((BigQueryDataSource) dataSource).setProjectId(PROJECT_ID);
            ((BigQueryDataSource) dataSource).setDatasetId(DATASET_ID);
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM crmsm-278014.testlegendEngine.game_post_wide limit 60";
            ResultSet resultSet = statement.executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++)
            {
                //String columnLabel = resultSet.getMetaData().getColumnLabel(i);
                //System.out.println("Column Level: " + columnLabel);
                
                //System.out.println(i);
                //int columnType = resultSet.getMetaData().getColumnType(i);
                //System.out.println("Column Type: " + columnType);
                
                //System.out.println(resultSet.getDate(columnLabel));
                //System.out.println(resultSet.getTimestamp(i, null));
                
                
            }
            
            int index = 1; 
            while (resultSet.next()) 
            {
                //String value = resultSet.getString(index);
                //System.out.println("Value at index: " + index + " is: " + value);
                String gameId = resultSet.getString("gameId"); 
                System.out.println("gameId: " + gameId);
                //Object value1 = resultSet.getObject(index);
                //System.out.println(value1);
                
                //java.util.Date date = resultSet.getDate(index);
                //System.out.println(date);
                //String columnLabel = resultSet.getMetaData().getColumnLabel(1);
                //System.out.println(resultSet.getDate(columnLabel));*/
            }

            connection.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }

}
