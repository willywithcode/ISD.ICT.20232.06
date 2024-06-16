package views.screen.invoice;

import entity.order.OrderMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Utils;
import views.screen.FXMLScreenHandler;

import java.io.IOException;
import java.sql.SQLException;

public class MediaInvoiceScreenHandler extends FXMLScreenHandler {

    @FXML
    private HBox hboxMedia;

    @FXML
    private VBox imageLogoVbox;

    @FXML
    private ImageView image;

    @FXML
    private VBox description;

    @FXML
    private Label title;

    @FXML
    private Label numOfProd;

    @FXML
    private Label labelTypeOfDelivery;

    @FXML
    private Label price;

    private OrderMedia orderMedia;

    public MediaInvoiceScreenHandler(String screenPath) throws IOException {
        super(screenPath);
    }


    /**
     * @param orderMedia
     * @throws SQLException
     */
    public void setOrderMedia(OrderMedia orderMedia) throws SQLException {
        this.orderMedia = orderMedia;
        setMediaInfo();
    }


    /**
     * @throws SQLException
     */
    public void setMediaInfo() throws SQLException {
        title.setText(orderMedia.getMedia().getTitle());
        price.setText(Utils.getCurrencyFormat(orderMedia.getPrice()));
        numOfProd.setText(String.valueOf(orderMedia.getQuantity()));
        setImage(image, orderMedia.getMedia().getImageURL());
        image.setPreserveRatio(false);
        image.setFitHeight(90);
        image.setFitWidth(83);
    }
    /**
     * param boolean
     */
    public void setTypeOfDelivery(boolean isRush) {
    	this.setVisibleTypeOfDelivery(true);
    	if(isRush) {
    		labelTypeOfDelivery.setText("Rush");
    	}
    	else {
    		labelTypeOfDelivery.setText("Normal");
    	}
    }
    /**
     * param boolean
     */
    public void setVisibleTypeOfDelivery(boolean state) {
    	labelTypeOfDelivery.setVisible(state);
    }

}
