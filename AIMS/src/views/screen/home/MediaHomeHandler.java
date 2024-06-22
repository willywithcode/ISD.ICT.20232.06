package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import common.exception.MediaNotAvailableException;
import entity.cart.Cart;
import entity.cart.CartMedia;
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
import views.screen.popup.PopupScreen;

public class MediaHomeHandler extends FXMLScreenHandler {

    private static Logger LOGGER = Utils.getLogger(MediaHomeHandler.class.getName());
    @FXML
    protected ImageView mediaImage;
    @FXML
    protected Label supportedRushDelivery;
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

    public MediaHomeHandler(String screenPath, Media media, HomeScreenHandler home) throws SQLException, IOException {
        super(screenPath);
        this.media = media;
        this.home = home;
        this.supportedRushDelivery.setVisible(media.getIsSupportedPlaceRushOrder());
        addToCartBtn.setOnMouseClicked(event -> {
        	CartMedia mediaInCart = home.getBController().checkMediaInCart(media);
            try {
                int avail = media.getQuantity()- (mediaInCart != null ? mediaInCart.getQuantity():0);
                if (spinnerChangeNumber.getValue() > avail) throw new MediaNotAvailableException();
                Cart cart = Cart.getCart();
                // if media already in cart then we will increase the quantity by 1 instead of create the new cartMedia
                if (mediaInCart != null) {
                    mediaInCart.setQuantity(mediaInCart.getQuantity() + spinnerChangeNumber.getValue());
                } else {
                	mediaInCart = new CartMedia(media, cart, spinnerChangeNumber.getValue(), media.getPrice());
                    cart.getListMedia().add(mediaInCart);
                }
                avail =  media.getQuantity()-mediaInCart.getQuantity();
//                media.setQuantity(media.getQuantity() - spinnerChangeNumber.getValue());
                mediaAvail.setText(String.valueOf(avail));
                home.getNumMediaCartLabel().setText(String.valueOf(cart.getTotalMedia()));
                PopupScreen.success("The media " + media.getTitle() + " added to Cart");
            } catch (MediaNotAvailableException exp) {
                try {
                	int avail = media.getQuantity()- (mediaInCart != null ? mediaInCart.getQuantity():0);
                    String message = "Not enough media:\nRequired: " + spinnerChangeNumber.getValue() + "\nAvail: " + avail;
                    LOGGER.severe(message);
                    PopupScreen.error(message);
                } catch (Exception e) {
                    LOGGER.severe("Cannot add media to cart: ");
                }

            } catch (Exception exp) {
                LOGGER.severe("Cannot add media to cart: ");
                exp.printStackTrace();
            }
        });
        viewBtn.setOnMouseClicked(event -> {
        	home.openMediaDetail(media);
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
        CartMedia mediaInCart = home.getBController().checkMediaInCart(media);
        mediaImage.setFitHeight(160);
        mediaImage.setFitWidth(152);
        mediaImage.setImage(image);

        mediaTitle.setText(media.getTitle());
        mediaPrice.setText(Utils.getCurrencyFormat(media.getPrice()));
        mediaAvail.setText(Integer.toString(media.getQuantity()- (mediaInCart != null ? mediaInCart.getQuantity():0)));
        spinnerChangeNumber.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)
        );

        setImage(mediaImage, media.getImageURL());
    }

}
