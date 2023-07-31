package org.finos.legend.engine.bigquery.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;

import org.finos.legend.engine.bigquery.BigQueryConnection;
import org.finos.legend.engine.bigquery.connection.test.BigQueryConnectionTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.ServiceOptions;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.ConnectionSettings;
import com.google.cloud.bigquery.DatasetId;

public class ExternalIntegration_TestConnectionAcquisitionWithFlowProvider_BigQuery extends BigQueryConnectionTest{
    private BigQuery bigquery;
    private BigQueryOptions options;
    private ConnectionSettings connectionSettings;
    private ExternalIntegration_TestConnectionAcquisitionWithFlowProvider_BigQuery connection;
    public static final String GOOGLE_APPLICATION_CREDENTIALS = "GOOGLE_APPLICATION_CREDENTIALS";
    private static final String PROJECT_ID = "crmsm-278014";
    private static final String DATASET_ID = "testlegendEngine";
    private static final String DEFAULT_TEST_DATASET = "bigquery_test_dataset";
    private static final int DEFAULT_PAGE_SIZE = 10000;
    
    @BeforeClass
    public static void verifyTestSetup() {
        String googleApplicationCredentials = System.getenv(GOOGLE_APPLICATION_CREDENTIALS);
        if (googleApplicationCredentials == null || googleApplicationCredentials.trim().isEmpty())
        {
            fail(String.format("Tests cannot be run. GCP env variable %s has not been set", GOOGLE_APPLICATION_CREDENTIALS));
        }
    }
    
    @Before
    public void setUp() {
    	options = createBigQueryOptionsForProject(PROJECT_ID);
        bigquery = options.getService();

        connectionSettings =
            ConnectionSettings.newBuilder()
                .setDefaultDataset(DatasetId.of(DEFAULT_TEST_DATASET))
                .setNumBufferedRows(DEFAULT_PAGE_SIZE)
                .build();
        bigquery =
            options
                .toBuilder()
                .setRetrySettings(ServiceOptions.getDefaultRetrySettings())
                .build()
                .getService();
        connection = (ExternalIntegration_TestConnectionAcquisitionWithFlowProvider_BigQuery) bigquery.createConnection(connectionSettings);
        assertNotNull(connection);
    }

	private BigQueryOptions createBigQueryOptionsForProject(String projectId) {
		return BigQueryOptions.newBuilder()
		        .setProjectId(projectId)
		        .setRetrySettings(ServiceOptions.getNoRetrySettings())
		        .build();
	}
	
	@Test
	public void testBigQueryGCPADCConnection_subject() {
		Connection connection = new BigQueryConnection(PROJECT_ID, DATASET_ID);
		testConnection(connection, 1, "select * from `crmsm-278014.testlegendEngine.game_post_wide`");
	}

}
