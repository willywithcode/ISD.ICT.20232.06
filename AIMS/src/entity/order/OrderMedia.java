package entity.order;

import entity.db.AIMSDB;
import entity.media.Media;

import java.io.Console;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderMedia {

    private Media media;
    private int price;
    private int quantity;

    public OrderMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }
    public void createOrderMediaEntity(Integer id) {
        System.out.println("Creating order media entity");
        String query = "INSERT INTO 'OrderMedia' (mediaID, orderID, price, quantity) " +
                "VALUES ( ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, this.media.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, this.price * quantity);
            preparedStatement.setInt(4, this.quantity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
                "  media='" + media + "'" +
                ", quantity='" + quantity + "'" +
                ", price='" + price + "'" +
                "}";
    }


    /**
     * @return Media
     */
    public Media getMedia() {
        return this.media;
    }


    /**
     * @param media
     */
    public void setMedia(Media media) {
        this.media = media;
    }


    /**
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }


    /**
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * @return int
     */
    public int getPrice() {
        return this.price;
    }


    /**
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

}
