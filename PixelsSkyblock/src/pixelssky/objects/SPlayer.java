package pixelssky.objects;

import java.util.ArrayList;
import pixelssky.utils.StringConverter;
import java.sql.*;

public class SPlayer {
	private String UUID;
	private ArrayList<Data> data;
	private ArrayList<Right> rights;
	private Island island;
	private ScoreboardObject sb;

	// TODO : Get and set

	// Database
	static final String DB_URL = "";
	static final String USER = "username";
	static final String PASSWORD = "password";

	// Connection
	Connection conn;
	Statement stmt;

	SPlayer(String UUID) {
		// TODO : Load or create player data
		conn = null;
		stmt = null;
	}

	public void saveData() {
		// TODO : Save all player data, UUID, Island ID and rights
		// Note : Data has to be saved in string
		// Note : Rights has to be saved in string

		String sql = "INSERT INTO";

		for (Data d : data) {

		}
		for (Right r : rights) {

		}

		try {

			// Connection
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			stmt = conn.createStatement();
			
			// Request
			stmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getUUID() {
		return UUID;
	}

	public Data getData(String dataName) {
		for (Data d : data) {
			if (d.getDataName().equals(d)) {
				return d;
			}
		}
		return null;
	}

	public void addOrSetData(String dataName, Object data) {
		Data d = getData(dataName);
		if (d == null) {
			this.data.add(new Data(dataName, data));
		} else {
			d.setData(data);
		}
	}

}
