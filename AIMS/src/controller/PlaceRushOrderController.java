package controller;

//import entity.shipping.Shipment;
import strategy.placeOrder.IPlaceOrderStrategy;
import strategy.placeOrder.NormalPlaceOrder;
import strategy.placeOrder.RushPlaceOrder;
import views.screen.invoice.InvoiceScreenHandler;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * This class controls the flow of place rush order usecase in our AIMS project
 * Fuoctional cohesion is high because it only handles the place rush order process. Communication cohesion is high because it only communicates with the PlaceOrderStrategy entity
 */
public class PlaceRushOrderController extends BaseController {
    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());
    private IPlaceOrderStrategy placeOrderStrategy;

    /**
     * @param deliveryData
     * @param typeDelivery
     * Data coupling, control coupling because it is passing data to another class
     */
    public void validatePlaceRushOrderData(int typeDelivery, InvoiceScreenHandler invoiceScreen) {
    	
        if (typeDelivery== utils.Configs.PLACE_RUSH_ORDER) {
           // validate
        	this.SetTypePlaceOrder(new RushPlaceOrder());
        }
        else {
        	this.SetTypePlaceOrder(new NormalPlaceOrder());
        }
        this.PlaceOrder(invoiceScreen);
    }
    /**
     * @return void
     * param IPlaceOrderStrategy
     */
    public void PlaceOrder(InvoiceScreenHandler invoiceScreen) {

        placeOrderStrategy.PlaceOrder(invoiceScreen);
    }
    /**
     * @return void
     * param IPlaceOrderStrategy
     * Data coupling, control coupling because it is passing data to another class
     * This method is used to set the type of place order
     * @param placeOrderStrategy
     * @return void
     * @SRP This class is not violating the Single Responsibility Principle because it is responsible for managing the place order and it is not responsible for other tasks.
     * Dependency inversion principle is applied here because the PlaceRushOrderController class depends on the IPlaceOrderStrategy interface, not on the concrete classes.
     */
    public void SetTypePlaceOrder(IPlaceOrderStrategy placeOrderStrategy) {
    	this.placeOrderStrategy = placeOrderStrategy;
    }
}
