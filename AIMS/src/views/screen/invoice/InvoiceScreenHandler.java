package views.screen.invoice;

import common.exception.ProcessInvoiceException;
import controller.PaymentController;
import entity.invoice.Invoice;
import entity.order.OrderMedia;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.payment.PaymentScreenHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class InvoiceScreenHandler extends BaseScreenHandler implements Initializable{

    private static Logger LOGGER = Utils.getLogger(InvoiceScreenHandler.class.getName());

    @FXML
    private Label pageTitle;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label province, district, ward;
    
    @FXML
    private Label email;
    
    @FXML
    private Label deliveryTime, deliveryTimeLabel;

    @FXML
    private Label address;

    @FXML
    private Label instructions;

    @FXML
    private Label subtotal;
    
    @FXML
    private Label feeRushDelivery;

    @FXML
    private Label shippingFees;

    @FXML
    private Label total;

    @FXML
    private VBox vboxItems;

    private Invoice invoice;

    public Invoice getInvoice() {
    	return invoice;
    }
    public InvoiceScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;
    }

    /**
     * @param event
     */
    @FXML
    private void handleBack(MouseEvent event){
        // Back to previous screen
    	this.getPreviousScreen().show();
    }
    
    public void setInvoiceInfo() {

        name.setText(invoice.getOrder().getName());
        phone.setText(invoice.getOrder().getPhone());
        email.setText(invoice.getOrder().getEmail());
        province.setText(invoice.getOrder().getProvince());
        district.setText(invoice.getOrder().getDistrict());
        ward.setText(invoice.getOrder().getWard());
        instructions.setText(invoice.getOrder().getInstruction());
        address.setText(invoice.getOrder().getAddress());
        subtotal.setText(Utils.getCurrencyFormat(invoice.getOrder().getAmount()));
        shippingFees.setText(Utils.getCurrencyFormat(invoice.getOrder().getShippingFees()));
        
        if(invoice.getOrder().getDeliveryTime() == null) {
        	deliveryTime.setVisible(false);
        	deliveryTimeLabel.setVisible(false);
        }else {
        	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	String formattedDeliveryDate = invoice.getOrder().getDeliveryTime().format(dateFormatter);
        	deliveryTime.setText(formattedDeliveryDate);	
        }
        
        int amount = invoice.getOrder().getAmount() + invoice.getOrder().getShippingFees() + invoice.getOrder().getRush_shipping_fee();
        total.setText(Utils.getCurrencyFormat(amount));

//        invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {});
       
    }
    /**
     * @param boolean
     */
    public void setlsOrderMedia(boolean isRush) {
    	invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
            try {
                MediaInvoiceScreenHandler mis = new MediaInvoiceScreenHandler(Configs.INVOICE_MEDIA_SCREEN_PATH);
                mis.setOrderMedia((OrderMedia) orderMedia);
                if(isRush) {
                	mis.setTypeOfDelivery(orderMedia.getMedia().getIsSupportedPlaceRushOrder());
                }else {
                	mis.setVisibleTypeOfDelivery(false);
                }
                vboxItems.getChildren().add(mis.getContent());
                
            } catch (IOException | SQLException e) {
                System.err.println("errors: " + e.getMessage());
                throw new ProcessInvoiceException(e.getMessage());
            }

        });
    }
    /**
     * @param boolean
     */
    public void setFeeRushDelivery(boolean isRush) {
    	if(!isRush) {
    		feeRushDelivery.setVisible(false);
    		return;
    	}
    	feeRushDelivery.setVisible(true);
    	feeRushDelivery.setText("Rush :  " + Utils.getCurrencyFormat(invoice.getOrder().getRush_shipping_fee() ));
    }
    /**
     * @param 
     */
    public int getNumRushMedia() {
    	int count = 0;
    	for (OrderMedia orderMedia : this.invoice.getOrder().getlstOrderMedia()) {
    		if(orderMedia.getMedia().getIsSupportedPlaceRushOrder()) {
    			count += orderMedia.getQuantity();
    		}
    	}
        System.out.println("count: " + count);
    	return count;
    	
    }
    /**
     * @param event
     * @throws IOException
     */
    @FXML
    void confirmInvoice(MouseEvent event) throws IOException {
    	this.invoice.getOrder().setOrderDate(LocalDateTime.now());
    	this.invoice.getOrder().setStatus("pending");
        this.invoice.getOrder().setShippingFees(this.invoice.getOrder().getShippingFees() + this.invoice.getOrder().getRush_shipping_fee());
        this.invoice.getOrder().setTotal_price(this.invoice.getOrder().getAmount() + this.invoice.getOrder().getShippingFees());
        this.invoice.getOrder().setShippingStatus("pending payment");
    	this.invoice.getOrder().createOrderEntity();
        BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, invoice);
        paymentScreen.setBController(new PaymentController());
        paymentScreen.setPreviousScreen(this);
        paymentScreen.setHomeScreenHandler(homeScreenHandler);
        paymentScreen.setScreenTitle("Payment Screen");
        paymentScreen.show();

    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

}
