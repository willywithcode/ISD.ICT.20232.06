package views.screen.cart;

import common.exception.MediaNotAvailableException;
import common.exception.PlaceOrderException;
import controller.PlaceOrderController;
import controller.ViewCartController;
import entity.cart.CartMedia;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CartScreenHandler extends BaseScreenHandler {

    private static Logger LOGGER = Utils.getLogger(CartScreenHandler.class.getName());
    @FXML
    VBox vboxCart;
    @FXML
    private ImageView aimsImage;
    @FXML
    private Label pageTitle;
    @FXML
    private Label shippingFees;

    @FXML
    private Label labelAmount;

    @FXML
    private Label labelSubtotal;

    @FXML
    private Label labelVAT;

    @FXML
    private Button btnPlaceOrder;

    public CartScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);

        // fix relative image path caused by fxml
        File file = new File("assets/images/Logo.png");
        Image im = new Image(file.toURI().toString());
        aimsImage.setImage(im);

        // on mouse clicked, we back to home
        aimsImage.setOnMouseClicked(e -> {
            homeScreenHandler.show();
        });

        // on mouse clicked, we start processing place order usecase
        btnPlaceOrder.setOnMouseClicked(e -> {

        });
    }


    /**
     * @return Label
     */
    public Label getLabelAmount() {
        return labelAmount;
    }


    /**
     * @return Label
     */
    public Label getLabelSubtotal() {
        return labelSubtotal;
    }


    /**
     * @return ViewCartController
     */
    public ViewCartController getBController() {
        return (ViewCartController) super.getBController();
    }


    /**
     * @param prevScreen
     * @throws SQLException
     */
    public void requestToViewCart(BaseScreenHandler prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        setScreenTitle("Cart Screen");
        getBController().checkAvailabilityOfProduct();
        displayCartWithMediaAvailability();
        show();
    }


    /**
     * @throws SQLException
     * @throws IOException
     */
    public void requestToPlaceOrder() {
    	
    }


    /**
     * @throws SQLException
     */
    public void updateCart() throws SQLException {
        getBController().checkAvailabilityOfProduct();
        displayCartWithMediaAvailability();
    }

    void updateCartAmount() {
        // calculate subtotal and amount
        int subtotal = getBController().getCartSubtotal();
        int vat = (int) ((Configs.PERCENT_VAT / 100) * subtotal);
        int amount = subtotal + vat;


        // update subtotal and amount of Cart
        labelSubtotal.setText(Utils.getCurrencyFormat(subtotal));
        labelVAT.setText(Utils.getCurrencyFormat(vat));
        labelAmount.setText(Utils.getCurrencyFormat(amount));
    }

    private void displayCartWithMediaAvailability() {
        // clear all old cartMedia
        vboxCart.getChildren().clear();

        // get list media of cart after check availability
        List lstMedia = getBController().getListCartMedia();

        try {
            for (Object cm : lstMedia) {

                // display the attribute of vboxCart media
                CartMedia cartMedia = (CartMedia) cm;
                MediaHandler mediaCartScreen = new MediaHandler(Configs.CART_MEDIA_PATH, this);
                mediaCartScreen.setCartMedia(cartMedia);

                // add spinner
                vboxCart.getChildren().add(mediaCartScreen.getContent());
            }
            // calculate subtotal and amount
            updateCartAmount();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
