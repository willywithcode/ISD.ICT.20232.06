package views.screen.shipping;

import common.exception.InvalidDeliveryInfoException;
import controller.PlaceOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShippingScreenHandler extends BaseScreenHandler implements Initializable {

    @FXML
    private Label screenTitle;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField address;

    @FXML
    private TextField instructions;

    @FXML
    private ComboBox<String> province;

    private Order order;

    public ShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        this.province.getItems().addAll(Configs.PROVINCES);
    }

    /**
     * @param event
     * @throws IOException
     * @throws InterruptedException
     * @throws SQLException
     */
    @FXML
    void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

        // add info to messages
        HashMap messages = new HashMap<>();
        messages.put("name", name.getText());
        messages.put("phone", phone.getText());
        messages.put("address", address.getText());
        messages.put("instructions", instructions.getText());
        messages.put("province", province.getValue());
        var placeOrderCtrl = getBController();
        if (!placeOrderCtrl.validateContainLetterAndNoEmpty(name.getText())) {
            PopupScreen.error("Name is not valid!");
            return;
        }
        if (!placeOrderCtrl.validatePhoneNumber(phone.getText())) {
            PopupScreen.error("Phone is not valid!");
            return;

        }
        if (!placeOrderCtrl.validateContainLetterAndNoEmpty(address.getText())) {
            PopupScreen.error("Address is not valid!");
            return;
        }
        if (province.getValue() == null) {
            PopupScreen.error("Province is empty!");
            return;
        }
        try {
            // process and validate delivery info
            getBController().processDeliveryInfo(messages);
        } catch (InvalidDeliveryInfoException e) {
            throw new InvalidDeliveryInfoException(e.getMessage());
        }

        // calculate shipping fees
        int shippingFees = getBController().calculateShippingFee(order.getAmount());
        order.setShippingFees(shippingFees);
        order.setName(name.getText());
        order.setPhone(phone.getText());
        order.setProvince(province.getValue());
        order.setAddress(address.getText());
        order.setInstruction(instructions.getText());


//        // // create invoice screen
//        Invoice invoice = getBController().createInvoice(order);
//        BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
//        InvoiceScreenHandler.setPreviousScreen(this);
//        InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
//        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
//        InvoiceScreenHandler.setBController(getBController());
//        InvoiceScreenHandler.show();

        //create delivery method screen
        BaseScreenHandler DeliveryMethodsScreenHandler = new DeliveryMethodsScreenHandler(this.stage, Configs.DELIVERY_METHODS_PATH, this.order);
        DeliveryMethodsScreenHandler.setPreviousScreen(this);
        DeliveryMethodsScreenHandler.setHomeScreenHandler(homeScreenHandler);
        DeliveryMethodsScreenHandler.setScreenTitle("Delivery method screen");
        DeliveryMethodsScreenHandler.setBController(getBController());
        DeliveryMethodsScreenHandler.show();
    }

    /**
     * @return PlaceOrderController
     */
    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }


}
