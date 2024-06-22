package views.screen.shipping;

import java.io.IOException;
import entity.order.OrderMedia;
import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import strategy.placeOrder.IPlaceOrderStrategy;
import strategy.placeOrder.NormalPlaceOrder;
import strategy.placeOrder.RushPlaceOrder;
public class DeliveryMethodsScreenHandler extends BaseScreenHandler {

    private Order order;

    @FXML
    private RadioButton placeRushOrderValue;

    @FXML
    private RadioButton placeOrderValue;

    @FXML
    private TextField deliveryInstruction;

    @FXML
    private TextField shipmentDetail;

    @FXML
    private DatePicker deliveryTime;

    @FXML
    private Label errorProvince;
    
    @FXML
    private Label errorNoSupportRushDeliveryMedia;

    @FXML
    private Button updateDeliveryMethodInfoButton;

    public DeliveryMethodsScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;
        this.handleDeliveryType();
        this.handleProvinceError();
    }
   
    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void updateDeliveryMethodInfo(MouseEvent event) throws IOException {
        String deliveryInstructionString = new String(deliveryInstruction.getText());
        String shipmentDetailString = new String(shipmentDetail.getText());
        String deliveryDateString = new String();
        if (deliveryTime.getValue() != null) {
            deliveryDateString = new String(deliveryTime.getValue().toString());
        }
        int typeDelivery;
        if (placeRushOrderValue.isSelected()) {
            typeDelivery = utils.Configs.PLACE_RUSH_ORDER;
        } else {
            typeDelivery = utils.Configs.PLACE_ORDER;
        }
//        var shipment = new Shipment(typeDelivery);
//        shipment.setShipmentDetail(shipmentDetailString);
//        shipment.setDeliveryTime(deliveryDateString);
//        shipment.setDeliveryInstruction(deliveryInstructionString);

//        order.setShipment(shipment);

        // // create invoice screen
        Invoice invoice = getBController().createInvoice(order);
        BaseScreenHandler invoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
        invoiceScreenHandler.setPreviousScreen(this);
        invoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        invoiceScreenHandler.setScreenTitle("Invoice Screen");
        invoiceScreenHandler.setBController(getBController());
        PlaceRushOrderController placeRushController = new PlaceRushOrderController();
        placeRushController.validatePlaceRushOrderData(typeDelivery, (InvoiceScreenHandler) invoiceScreenHandler);
        invoiceScreenHandler.show();
    }


    /**
     * @param event
     */
    @FXML
    private void handleBack(MouseEvent event){
        // Back to previous screen
    	this.getPreviousScreen().show();
    }


    /**
     * @param event
     */
    @FXML
    private void handleDeliveryType(ActionEvent event) {
        this.handleDeliveryType();
        handleProvinceError(event);
    }


    /**
     * @param event
     */
    @FXML
    private void handleProvinceError(ActionEvent event) {
        this.handleProvinceError();
    }

    /**
     * @return PlaceOrderController
     */
    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }
    /**
     * @return void
     */
    private void handleDeliveryType() {
        if (placeOrderValue.isSelected()) {
            deliveryInstruction.setDisable(true);
            shipmentDetail.setDisable(true);
            deliveryTime.setDisable(true);
        } else if (placeRushOrderValue.isSelected()) {
            deliveryInstruction.setDisable(false);
            shipmentDetail.setDisable(false);
            deliveryTime.setDisable(false);
        }
    }
    /**
     * @return void
     */
    private void handleProvinceError() {
        String province = new String(order.getProvince());

        errorProvince.setVisible(false);
        deliveryInstruction.setDisable(true);
        shipmentDetail.setDisable(true);
        deliveryTime.setDisable(true);
        updateDeliveryMethodInfoButton.setDisable(false);

        if (!province.equals("Hà Nội")) {
        	errorNoSupportRushDeliveryMedia.setVisible(false);
            if (placeRushOrderValue.isSelected()) {
                errorProvince.setVisible(true);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
                updateDeliveryMethodInfoButton.setDisable(true);
            } else {
                updateDeliveryMethodInfoButton.setDisable(false);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
            }
        } else {
            if (placeRushOrderValue.isSelected()) {
                errorProvince.setVisible(false);
                deliveryInstruction.setDisable(false);
                shipmentDetail.setDisable(false);
                deliveryTime.setDisable(false);
                updateDeliveryMethodInfoButton.setDisable(false);
                this.handleSupportRushDelivery();
            } else {
            	errorNoSupportRushDeliveryMedia.setVisible(false);
                updateDeliveryMethodInfoButton.setDisable(false);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
                errorProvince.setVisible(false);
            }
        }
    }
    /**
     * @return void
     */
    private void handleSupportRushDelivery() {
    	for (OrderMedia orderMedia : this.order.getlstOrderMedia()) {
    		if(orderMedia.getMedia().getIsSupportedPlaceRushOrder()) {
    			errorNoSupportRushDeliveryMedia.setVisible(false);
                deliveryInstruction.setDisable(false);
                shipmentDetail.setDisable(false);
                deliveryTime.setDisable(false);
                updateDeliveryMethodInfoButton.setDisable(false);
                return;
    		}
    	}
    	errorNoSupportRushDeliveryMedia.setVisible(true);
        deliveryInstruction.setDisable(true);
        shipmentDetail.setDisable(true);
        deliveryTime.setDisable(true);
        updateDeliveryMethodInfoButton.setDisable(true);
        
    }
    
}
