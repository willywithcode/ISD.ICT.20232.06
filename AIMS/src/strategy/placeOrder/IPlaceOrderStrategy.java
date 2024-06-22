package strategy.placeOrder;

import views.screen.invoice.InvoiceScreenHandler;

public interface IPlaceOrderStrategy {

	/**
	 * This method is used to place an order.
	 *
	 * @param invoiceScreen The invoice screen handler.
	 */
	public void PlaceOrder(InvoiceScreenHandler invoiceScreen);
}
