package views.screen.shipping;

import common.exception.InvalidDeliveryInfoException;
import controller.PlaceOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private TextField email;
    
    @FXML
    private DatePicker deliveryTime;
    
    @FXML
    private Label deliveryTimeLabel;

    @FXML
    private TextField instructions;

    @FXML
    private ChoiceBox<String> province, district, ward;
    
    @FXML
    private ChoiceBox<String> shippingType;

    private Order order;
    
    private String storeLocation = "HH1C, Linh đàm, hoàng liệt, hoàng mai, hà nội"; // Store's address

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
        
//        List<String> shipment = Arrays.asList("Rush shipping", "normal shipping");
//        ObservableList<String> shipments = FXCollections.observableArrayList(shipment);
//        shippingType.setItems(shipments);
        
        deliveryTimeLabel.setVisible(false);
		deliveryTime.setVisible(false);
        
        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
//        
//        this.province.getItems().addAll(Configs.PROVINCES);
        List<String> provinceList = Utils.getProvincesList();
        ObservableList<String> provinces = FXCollections.observableArrayList(provinceList);
        province.setItems(provinces);
        province.setOnAction(event -> {
        	updateDistrict((String) province.getValue());
        });
//        province.setItems(FXCollections.observableArrayList(provinceList));
        
        shippingType.setOnAction(event -> {
        	updateForm((String) shippingType.getValue());
        });
        
        deliveryTime.setDayCellFactory(getDateCellFactory());
    }
    
    private void updateDistrict(String selectedProvince) {
    	if(selectedProvince.equals("Thành phố Hà Nội")) {
    		List<String> shipment = Arrays.asList("rush shipping", "normal shipping");
    		ObservableList<String> shipments = FXCollections.observableArrayList(shipment);
    		shippingType.setItems(shipments);
    	}else {
    		shippingType.setItems(FXCollections.observableArrayList("normal shipping"));
    	}
    	if(selectedProvince != null) {
    		List<String> districtsList = Utils.getDistrictsList(selectedProvince);
    		ObservableList<String> districts = FXCollections.observableArrayList(districtsList);
    		
    		district.setOnAction(event -> {
    			updateWard((String) district.getValue());
    		});
    		if(!districts.isEmpty()) {
    			district.setValue(districts.get(0));
    			district.setItems(districts);
    		}
    	}
    }
    
    private void updateWard(String selectedDistrict) {
    	if(selectedDistrict != null) {
    		List<String> wardsList = Utils.getWardsList(selectedDistrict);
    		ObservableList<String> wards = FXCollections.observableArrayList(wardsList);
    		if(!wards.isEmpty()) {
    			ward.setValue(wards.get(0));
    			ward.setItems(wards);
    		}
    	}
    }
    
    private void updateForm(String shippingType) {
    	if(shippingType.equals("rush shipping")) {
    		deliveryTimeLabel.setVisible(true);
    		deliveryTime.setVisible(true);
    	}else {
    		deliveryTimeLabel.setVisible(false);
    		deliveryTime.setVisible(false);
    	}
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
        messages.put("province", province.getValue().toString());
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
        String fullAddress = address.getText() + ", " + ward.getValue() + ", " + district.getValue() + ", " + province.getValue();
        order.setShippingFees(shippingFees);
        order.setName(name.getText());
        order.setPhone(phone.getText());
        order.setProvince(province.getValue().toString());
        order.setAddress(fullAddress);
        order.setInstruction(instructions.getText());
        order.setDistrict(district.getValue());
        order.setWard(ward.getValue());
        order.setShippingType(shippingType.getValue());
        order.setEmail(email.getText());
        
        if(shippingType.getValue().equals("rush shipping")) {
        	order.setDeliveryTime(deliveryTime.getValue());
        }
        
        Invoice invoice = getBController().createInvoice(order);
        BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
        InvoiceScreenHandler.setPreviousScreen(this);
        InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
        InvoiceScreenHandler.setBController(getBController());
        InvoiceScreenHandler.show();
        
//        //create delivery method screen
//        BaseScreenHandler DeliveryMethodsScreenHandler = new DeliveryMethodsScreenHandler(this.stage, Configs.DELIVERY_METHODS_PATH, this.order);
//        DeliveryMethodsScreenHandler.setPreviousScreen(this);
//        DeliveryMethodsScreenHandler.setHomeScreenHandler(homeScreenHandler);
//        DeliveryMethodsScreenHandler.setScreenTitle("Delivery method screen");
//        DeliveryMethodsScreenHandler.setBController(getBController());
//        DeliveryMethodsScreenHandler.show();
    }

    /**
     * @return PlaceOrderController
     */
    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }
    
    private Callback<DatePicker, DateCell> getDateCellFactory() {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                    }
                };
            }
        };
    }


}
