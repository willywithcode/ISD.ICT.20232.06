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
 *
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
    public void SetTypePlaceOrder(IPlaceOrderStrategy placeOrderStrategy) {
    	this.placeOrderStrategy = placeOrderStrategy;
    }
}
