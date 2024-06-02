package entity.db;

import utils.Utils;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class AIMSDB {
	
	private static Logger LOGGER = Utils.getLogger(Connection.class.getName());
    private static Connection connect;


    /**
     * @return Connection
     */
    public static Connection getConnection() {
        if (connect != null) {
            return connect;
        }
        try {
//            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/AIMS_Store";
            String user = "postgres";
            String password = "Dung03062002?";
            connect = DriverManager.getConnection(url, user, password);
            LOGGER.info("Connect database successfully");
        } catch (Exception e) {
        	LOGGER.info("Connect database fail");
            LOGGER.info(e.getMessage());
            System.out.print(e);
        }
        return connect;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        AIMSDB.getConnection();
    }

}