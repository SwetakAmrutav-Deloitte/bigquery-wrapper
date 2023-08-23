package com.deloitte.legend.engine.bigquery.predefined;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;

import com.deloitte.legend.engine.bigquery.BigQueryConnection;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.BigQueryResult;
import com.google.cloud.bigquery.BigQuerySQLException;

public class MainClass {
	
	private static final String PROJECT_ID = "crmsm-278014";
	private static final String DATASET_ID = "testlegendEngine";
	public static final String GOOGLE_APPLICATION_CREDENTIALS = "GOOGLE_APPLICATION_CREDENTIALS";
	
	public static void main(String[] args) throws BigQuerySQLException {
		
		BigQuery bigQuery = bigQueryConnection(PROJECT_ID, DATASET_ID);
		
		com.google.cloud.bigquery.Connection connection = bigQuery.createConnection();
		
		String sql = "SELECT * FROM crmsm-278014.testlegendEngine.game_post_wide limit 60";
		BigQueryResult bigQueryResult = connection.executeSelect(sql);
	}

	private static BigQuery bigQueryConnection(String projectId, String datasetId) {
		BigQuery bigQuery;
		String googleApplicationCredentials = System.getenv(GOOGLE_APPLICATION_CREDENTIALS);
		File credentialsPath = new File(googleApplicationCredentials);
		GoogleCredentials credentials = null;
		try {
			FileInputStream serviceAccountStream = new FileInputStream(credentialsPath);
			credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		bigQuery = BigQueryOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build().getService();
		return bigQuery;
	}
}
