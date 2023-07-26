package org.finos.legend.engine.bigquery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainClass {

	private static final String PROJECT_ID = "crmsm-278014";
	
	public static void main(String[] args) {
		try {
			Connection connection = new BigQueryConnection(PROJECT_ID);
			Statement statement = connection.createStatement();
			String sql = "SELECT gameId FROM crmsm-278014.testlegendEngine.game_post_wide limit 10";
			ResultSet resultSet = statement.executeQuery(sql);
			String gameId = resultSet.getString("gemeId");
			System.out.println(gameId);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
