package entity.order;

import entity.db.AIMSDB;
import entity.media.Media;

import java.io.Console;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderMedia {

    private Media media;
    private int price;
    private int quantity;

    public OrderMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }
    public OrderMedia() {
		// TODO Auto-generated constructor stub
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
    
    public List<OrderMedia> getOrderMediaList(int id){
        List<OrderMedia> orderMediaList = new ArrayList<>();
        String orderMediaQuery = "select * from OrderMedia where orderID = ?";
        String mediaQuery = "select * from Media where id = ?";
        
        try (PreparedStatement orderMediaStm = AIMSDB.getConnection().prepareStatement(orderMediaQuery);
             PreparedStatement mediaStm = AIMSDB.getConnection().prepareStatement(mediaQuery)) {          
            orderMediaStm.setInt(1, id);
            ResultSet res = orderMediaStm.executeQuery();
            int count = 0;
            while (res.next()) {
                int mediaId = res.getInt("mediaID");
                int quantity = res.getInt("quantity");
                int total_price_media = res.getInt("price");
                
                mediaStm.setInt(1, mediaId);
                ResultSet res_media = mediaStm.executeQuery();
                
                if (res_media.next()) {
                    String mediaType = res_media.getString("type");
                    String mediaCategory = res_media.getString("category");
                    String mediaTitle = res_media.getString("title");
                    int mediaPrice = res_media.getInt("price");
                    
                    Media found_media = new Media(mediaId, mediaTitle, mediaCategory, mediaPrice, mediaType);
                    OrderMedia found_order_media = new OrderMedia(found_media, quantity, total_price_media);
                    orderMediaList.add(found_order_media);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderMediaList;
    }


}
