package controller;

import common.exception.PaymentException;
import common.exception.TransactionNotDoneException;
import common.exception.UnrecognizedException;
import entity.cart.Cart;
import entity.invoice.Invoice;
import mailClient.MailService;
import mailClient.MailServiceImpl;
import subsystem.VnPayInterface;
import subsystem.VnPaySubsystem;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Map;

/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 *
 */
public class PaymentController extends BaseController {


    /**
     * Represent the Interbank subsystem
     */
    private VnPayInterface vnPayService;
//    private CreditCard card;

    /**
     * Validate the input date which should be in the format "mm/yy", and then
     * return a {@link java.lang.String String} representing the date in the
     * required format "mmyy" .
     *
     * @param date - the {@link java.lang.String String} represents the input date
     * @return {@link java.lang.String String} - date representation of the required
     * format
     * @throws TransactionNotDoneException - if the string does not represent a valid date
     *                                     in the expected format
     */

    public Map<String, String> makePayment(Map<String, String> res, int orderId, String shippingID, MailService mailService, Invoice invoice) {
        Map<String, String> result = new Hashtable<String, String>();

        try {
            this.vnPayService = new VnPaySubsystem();
            var trans = vnPayService.makePaymentTransaction(res);
            trans.save(orderId, shippingID);
            result.put("RESULT", "PAYMENT SUCCESSFUL!");
            result.put("MESSAGE", "You have succesffully paid the order!");
            mailService.sendMail(invoice.getOrder().getEmail(), "Hoa don ban hang AIMS", invoice.getDetailInvoice());
        } catch (PaymentException | UnrecognizedException | SQLException ex) {
            result.put("MESSAGE", ex.getMessage());
            result.put("RESULT", "PAYMENT FAILED!");

        } catch (ParseException ex) {
            result.put("MESSAGE", ex.getMessage());
            result.put("RESULT", "PAYMENT FAILED!");
        }
        return result;
    }

    /**
     * Gen url thanh toán vnPay
     * @param amount
     * @param content
     * @return
     */
    
    /**
	 * Represent the Interbank subsystem
	 */
//	private InterbankInterface interbank;

	/**
	 * Validate the input date which should be in the format "mm/yy", and then
	 * return a {@link java.lang.String String} representing the date in the
	 * required format "mmyy" .
	 * 
	 * @param date - the {@link java.lang.String String} represents the input date
	 * @return {@link java.lang.String String} - date representation of the required
	 *         format
	 * @throws InvalidCardException - if the string does not represent a valid date
	 *                              in the expected format
	 */
//	private String getExpirationDate(String date) throws InvalidCardException { 
//		String[] strs = date.split("/");
//		if (strs.length != 2) {
//			throw new InvalidCardException();
//		}
//
//		String expirationDate = null;
//		int month = -1;
//		int year = -1;
//
//		try {
//			month = Integer.parseInt(strs[0]);
//			year = Integer.parseInt(strs[1]);
//			if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
//				throw new InvalidCardException();
//			}
//			expirationDate = strs[0] + strs[1];
//
//		} catch (Exception ex) {
//			throw new InvalidCardException();
//		}
//
//		return expirationDate;
//	}

	/**
	 * Pay order, and then return the result with a message.
	 * 
	 * @param amount         - the amount to pay
	 * @param contents       - the transaction contents
	 * @param cardNumber     - the card number
	 * @param cardHolderName - the card holder name
	 * @param expirationDate - the expiration date in the format "mm/yy"
	 * @param securityCode   - the cvv/cvc code of the credit card
	 * @return {@link java.util.Map Map} represent the payment result with a
	 *         message.
	 */
//	public Map<String, String> payOrder(int amount, String contents, String cardNumber, String cardHolderName,
//			String expirationDate, String securityCode) { 
//		Map<String, String> result = new Hashtable<String, String>();
//		result.put("RESULT", "PAYMENT FAILED!");
//		try {
//			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
//					getExpirationDate(expirationDate));
//
//			this.interbank = new InterbankSubsystem();
//			PaymentTransaction transaction = interbank.payOrder(card, amount, contents);
//
//			result.put("RESULT", "PAYMENT SUCCESSFUL!");
//			result.put("MESSAGE", "You have succesffully paid the order!");
//		} catch (PaymentException | UnrecognizedException ex) {
//			result.put("MESSAGE", ex.getMessage());
//		}
//		return result;
//	}
    
    public String getUrlPay(int amount, String content){
        vnPayService = new VnPaySubsystem();
        var url = vnPayService.generatePayUrl(amount, content);
        return url;
    }

    public void emptyCart(){ 
        Cart.getCart().emptyCart();
    }
}
//import subsystem.InterbankInterface;
//import subsystem.InterbankSubsystem;

/*
SRP: vì nó đảm nhận nhiều trách nhiệm.
OCP: vì lớp này có thể cần được sửa đổi khi phương thức thanh toán mới được giới thiệu.
DIP: phụ thuộc trực tiếp vào lớp cụ thể InterbankSubsystem thay vì sử dụng một giao diện.
 */
//stamp coupling, control coupling