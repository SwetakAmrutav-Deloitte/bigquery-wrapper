package org.finos.legend.engine.bigquery.test;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;

public class ExternalIntegration_TestConnectionAcquisitionWithFlowProvider_BigQuery
{
    public static final String GOOGLE_APPLICATION_CREDENTIALS = "GOOGLE_APPLICATION_CREDENTIALS";

    @BeforeClass
    public static void verifyTestSetup()
    {
        String googleApplicationCredentials = System.getenv(GOOGLE_APPLICATION_CREDENTIALS);
        if (googleApplicationCredentials == null || googleApplicationCredentials.trim().isEmpty())
        {
            fail(String.format("Tests cannot be run. GCP env variable %s has not been set", GOOGLE_APPLICATION_CREDENTIALS));
        }
    }

}
