package org.finos.legend.engine.bigquery;

import com.google.cloud.bigquery.TableResult;

public class ResultSet extends BigQueryResultSet{

	public ResultSet(TableResult result) {
		super(result);
	}

}
