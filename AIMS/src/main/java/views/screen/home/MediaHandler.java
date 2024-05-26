package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import entity.media.Media;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Utils;
import views.screen.FXMLScreenHandler;

public class MediaHandler extends FXMLScreenHandler {

    private static Logger LOGGER = Utils.getLogger(MediaHandler.class.getName());
    @FXML
    protected ImageView mediaImage;
    @FXML
    protected Label mediaTitle;
    @FXML
    protected Label mediaPrice;
    @FXML
    protected Label mediaAvail;
    @FXML
    protected Spinner<Integer> spinnerChangeNumber;
    @FXML
    protected Button addToCartBtn;
    @FXML
    protected Button viewBtn;
    
    
    private Media media;
    private HomeScreenHandler home;

    public MediaHandler(String screenPath, Media media, HomeScreenHandler home) throws SQLException, IOException {
        super(screenPath);
        this.media = media;
        this.home = home;
        addToCartBtn.setOnMouseClicked(event -> {
        });
        setMediaInfo();
    }


    /**
     * @return Media
     */
    public Media getMedia() {
        return media;
    }


    /**
     * @throws SQLException
     */
    private void setMediaInfo() throws SQLException {
        // set the cover image of media
        File file = new File(media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setFitHeight(160);
        mediaImage.setFitWidth(152);
        mediaImage.setImage(image);

        mediaTitle.setText(media.getTitle());
        mediaPrice.setText(Utils.getCurrencyFormat(media.getPrice()));
        mediaAvail.setText(Integer.toString(media.getQuantity()));
        spinnerChangeNumber.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
        );

        setImage(mediaImage, media.getImageURL());
    }

}
