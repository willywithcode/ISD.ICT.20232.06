package strategy.placeOrder;

import views.screen.invoice.InvoiceScreenHandler;

public class RushPlaceOrder implements IPlaceOrderStrategy {

	/**
	 * This method is used to place an order with rush delivery.
	 *
	 * @param invoiceScreen The invoice screen handler.
	 */

	@Override
	public void PlaceOrder(InvoiceScreenHandler invoiceScreen) {
		// TODO Auto-generated method stub
		this.CalculateRushShippingFee(invoiceScreen);
		invoiceScreen.setlsOrderMedia(true);
		invoiceScreen.setFeeRushDelivery(true);
		invoiceScreen.setInvoiceInfo();
	}
	public void CalculateRushShippingFee(InvoiceScreenHandler invoiceScreen) {
		System.out.println("numRushMedia: " + invoiceScreen.getNumRushMedia());
		invoiceScreen.getInvoice().getOrder().setRush_shipping_fee(invoiceScreen.getNumRushMedia() * 10);
	}
}
