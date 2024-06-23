package entity.payment;

import entity.db.AIMSDB;

import java.sql.*;
import java.util.Date;

public class PaymentTransaction {
    private String errorCode;
    private String transactionId;
    private String transactionContent;
    private int amount;
    private Integer orderID;
    private Date createdAt;

    public PaymentTransaction(String errorCode, String transactionId, String transactionContent,
                              int amount, Date createdAt) {
        super();
        this.errorCode = errorCode;


        this.transactionId = transactionId;
        this.transactionContent = transactionContent;
        this.amount = amount;
        this.createdAt = createdAt;
    }


    /**
     * @return String
     */
    public String getErrorCode() {
        return errorCode;
    }

    public String getTransactionContent() {
        return transactionContent;
    }

    public void save(int orderId, String shippingID) throws SQLException {
        this.orderID = orderId;
        System.out.println("Saving transaction");
        Statement stm = AIMSDB.getConnection().createStatement();
        String query = "INSERT INTO PaymentTransaction ( orderID, createAt, content) " +
                "VALUES ( ?, ?, ?)";
        try (PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.setDate(2, new java.sql.Date(createdAt.getTime()));
            preparedStatement.setString(3,transactionContent );

            preparedStatement.executeUpdate();
        }
        String query2 = "UPDATE 'Order' SET status = 'Paid' WHERE id = ?";
        try (PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(query2)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.executeUpdate();
        }
        String query3 = "UPDATE 'Order' SET genID = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(query3)) {
            preparedStatement.setString(1, shippingID);
            preparedStatement.setInt(2, orderID);
            preparedStatement.executeUpdate();
        }
        String query4 = "UPDATE 'Order' SET shipping_status = 'packaging' where id = ?";
        try (PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(query4)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.executeUpdate();
        }
    }

}
