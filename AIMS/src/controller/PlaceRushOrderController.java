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


    /**
     * @param deliveryData
     * @param typeDelivery
     */
//    public void validatePlaceRushOrderData(Shipment deliveryData, InvoiceScreenHandler invoiceScreen) {
//        if (deliveryData.getShipType() == utils.Configs.PLACE_RUSH_ORDER) {
//           // validate
//        	this.PlaceOrder(new RushPlaceOrder(), invoiceScreen);
//        }
//        else {
//        	this.PlaceOrder(new NormalPlaceOrder(), invoiceScreen);
//        }
//    }
    /**
     * @return void
     * param IPlaceOrderStrategy
     */
    public void PlaceOrder(IPlaceOrderStrategy placeOrderStrategy, InvoiceScreenHandler invoiceScreen) {
    	placeOrderStrategy.PlaceOrder(invoiceScreen);
    }
}
