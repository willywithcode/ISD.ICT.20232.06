package controller;

import entity.db.AIMSDB;
import entity.media.Media;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewOrderController extends  BaseController   {

    public ResultSet viewOrder(String orderID) throws SQLException {
        String sql = "SELECT * FROM 'Order' WHERE genID like '" + orderID +"'";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {
            return res;
        }
        return null;
    }
}
