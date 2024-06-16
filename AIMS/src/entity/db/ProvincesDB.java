package entity.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import utils.Utils;

public class ProvincesDB {

	private static Logger LOGGER = Utils.getLogger(Connection.class.getName());
    private static Connection connect;
    
    public static Connection connect() {
    	String url = "jdbc:postgresql://localhost:5432/Provinces";
    	String username = "postgres";
		String password = "Dung03062002?";
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connect successfully");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
    }
    
    public static void main(String[] args) {
    	ProvincesDB.connect();
    }
}
