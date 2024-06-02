package entity.media;
import entity.db.AIMSDB;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import utils.Utils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class CD extends Media {

    private static Logger LOGGER = Utils.getLogger(Media.class.getName());
    String artist;
    String recordLabel;
    String musicType;
    Date releasedDate;

    public CD() throws SQLException {

    }

    public CD(int id, String title, String category, int price, int value, int quantity, String type, String artist,
              String recordLabel, String musicType, Date releasedDate) throws SQLException {
        super(id, title, category, price, value, quantity, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " artist='" + artist + "'" + ", recordLabel='" + recordLabel + "'" + "'"
                + ", musicType='" + musicType + "'" + ", releasedDate='" + releasedDate + "'" + "}";
    }

    @Override
    public CD getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM " +
                "CD " +
                "INNER JOIN Media " +
                "ON Media.id = CD.id " +
                "where Media.id = " + id + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {
            String title = res.getString("title");
            String type = res.getString("type");
            int price = res.getInt("price");
            int value = res.getInt("value");
            String category = res.getString("category");
            String imageURL = res.getString("imageURL");
            int quantity = res.getInt("quantity");
            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String musicType = res.getString("musicType");
            Date releasedDate = res.getDate("releasedDate");
            CD cd = new CD(id, title, category, price, value, quantity, type, artist, recordLabel, musicType,
                    releasedDate);
            cd.imageURL = imageURL;
            return cd;
        } else {
            throw new SQLException();
        }
    }

    public void createCD(int id, String title, String category, int price, int value, int quantity, String type,
                         String artist, String recordLabel, String musicType, Date releasedDate, String imageUrl) {
        String insertMediaSql = "INSERT INTO Media (id, title, category, price, value, quantity, type, imageUrl) VALUES ("
                + id + ", '" + title + "', '" + category + "', " + price + ", " + value + ", " + quantity + ", '" + type
                + "', '" + imageUrl + "')";
        String insertCdSql = "INSERT INTO CD (id, artist, recordLabel, musicType, releasedDate) VALUES (" + id + ", '"
                + artist + "', '" + recordLabel + "', '" + musicType + "', '" + releasedDate + "')";

        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(insertMediaSql);
            stm.executeUpdate(insertCdSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCD(int id, String title, String category, int price, int value, int quantity, String type,
                         String artist, String recordLabel, String musicType, Date releasedDate) {
        String updateMediaSql = "UPDATE Media SET " + "title = '" + title + "', " + "category = '" + category + "', "
                + "price = " + price + ", " + "value = " + value + ", " + "quantity = " + quantity + ", " + "type = '"
                + type + "' " + "WHERE id = " + id;

        String updateCdSql = "UPDATE CD SET " + "artist = '" + artist + "', " + "recordLabel = '" + recordLabel + "', "
                + "musicType = '" + musicType + "', " + "releasedDate = '" + releasedDate + "' " + "WHERE id = " + id;

        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(updateMediaSql);
            stm.executeUpdate(updateCdSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCD(int id) {
        String deleteMediaSql = "DELETE FROM Media WHERE id = " + id;
        String deleteCdSql = "DELETE FROM CD WHERE id = " + id;

        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(deleteMediaSql);
            stm.executeUpdate(deleteCdSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getAllMedia() throws SQLException {
        String sql = "SELECT * FROM " + "CD " + "INNER JOIN Media " + "ON Media.id = CD.id " + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        ArrayList cdList = new ArrayList<>();
        while (res.next()) {
        	// from Media table
            int id = res.getInt(1);
            String title = res.getString("title");
            String type = res.getString("type");
//            String imageURL = res.getString("imageURL");
            int price = res.getInt("price");
            int value = res.getInt("value");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");

            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String musicType = res.getString("musicType");
            Date releasedDate = res.getDate("releasedDate");
            CD cd = new CD(id, title, category, price, value, quantity, type, artist, recordLabel, musicType,
                    releasedDate);
            cdList.add(cd);
        }
        return cdList;
    }
    
    public void getDetail(VBox vboxDetail) {
    	File file = new File(getImageURL());
        Image image = new Image(file.toURI().toString());
        ImageView cdImageView = new ImageView();
        cdImageView.setImage(image);
        cdImageView.setPreserveRatio(true);
        cdImageView.setFitWidth(400);
        
        Label cdTitle = new Label(getTitle());
        cdTitle.setFont(Font.font(40));
        
        Label cdArtist = new Label("Artist: " + getArtist());
        cdArtist.setFont(Font.font(20));
                
        Label cdRecordLabel = new Label("Record label: " + getRecordLabel());
        cdRecordLabel.setFont(Font.font(20));
        
        Label cdReleaseDate = new Label("Release date: " + getReleasedDate().toString());
        cdReleaseDate.setFont(Font.font(20));
        
        Label cdMusicType = new Label("Music type: " + getMusicType());
        cdMusicType.setFont(Font.font(20));
                
        vboxDetail.setAlignment(Pos.CENTER);
        vboxDetail.setSpacing(20);
        vboxDetail.getChildren().add(cdImageView);
        vboxDetail.getChildren().add(cdTitle);
        vboxDetail.getChildren().add(cdArtist);
        vboxDetail.getChildren().add(cdRecordLabel);
        vboxDetail.getChildren().add(cdReleaseDate);
        vboxDetail.getChildren().add(cdMusicType);
    }
}
