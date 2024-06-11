package entity.db;

import utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://aimsdb_lifedollhe:0d3525a33b60b252799b29ca307d3236534dc4a9@bqx.h.filess.io:3307/aimsdb_lifedollhe");
            LOGGER.info("Connect database successfully");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
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