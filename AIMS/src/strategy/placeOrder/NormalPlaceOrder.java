package strategy.placeOrder;

import entity.order.Order;
import views.screen.invoice.InvoiceScreenHandler;

public class NormalPlaceOrder implements IPlaceOrderStrategy {

	/**
	 * This method is used to place an order with normal delivery.
	 *
	 * @param invoiceScreen The invoice screen handler.
	 */

	@Override
	public void PlaceOrder(InvoiceScreenHandler invoiceScreen) {
		// TODO Auto-generated method stub
		invoiceScreen.setlsOrderMedia(false);
		invoiceScreen.setFeeRushDelivery(false);
	}

}
