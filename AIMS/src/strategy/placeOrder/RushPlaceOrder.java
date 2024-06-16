package strategy.placeOrder;

import views.screen.invoice.InvoiceScreenHandler;

public class RushPlaceOrder implements IPlaceOrderStrategy {

	@Override
	public void PlaceOrder(InvoiceScreenHandler invoiceScreen) {
		// TODO Auto-generated method stub
		invoiceScreen.setlsOrderMedia(true);
		invoiceScreen.setFeeRushDelivery(true);
	}
	
}
