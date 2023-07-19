package org.finos.legend.engine.bigquery;

public class Connection extends BigQueryConnection{
	
	public Connection(String projectId) {
		super(projectId);
	}
	private static final String PROJECT_ID = "crmsm-278014";
	
	public static void main(String[] args) {
		try {
			Connection connection = new Connection(PROJECT_ID);
			Statement statement = connection.createStatement();
			String sql = "SELECT gameId FROM crmsm-278014.testlegendEngine.game_post_wide limit 10";
			ResultSet rs = statement.executeQuery(sql);
			String gameId = rs.getString("gemeId");
			System.out.println(gameId);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}
