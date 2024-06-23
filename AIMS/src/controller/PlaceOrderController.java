package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

//control coupling, stamp coupling
/**
* This class controls the flow of place order usecase in our AIMS project
*/

// Vi phạm Single responsibility principle do lớp đang thực hiện cả chức năng 
// tính phí vận chuyển (method calculateShippingFee)
// kiểm tra thông tin đơn hàng (method validateDeliveryInfo)
// Cần tách các chức năng này ra 1 lớp riêng
public class PlaceOrderController extends BaseController {

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder
     * button
     *
     * @throws SQLException
     */
    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     *
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     *
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {

//        order.createOrderEntity();
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        validateDeliveryInfo(info);
    }

    /**
     * The method validates the info
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {

    }


    /**
     * @param phoneNumber
     * @return boolean
     * This method validates the phone number
     * @SRP This method is violating the Single Responsibility Principle because it is responsible for validating the phone number and calculating the shipping fee
     * Cpupling is high because it communicates with the Order entity
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10)
            return false;
        if (Character.compare(phoneNumber.charAt(0), '0') != 0)
            return false;
        try {
            Long.parseUnsignedLong(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


    /**
     * @param name
     * @return boolean
     * This method validates the name
     * @Coupling Coupling is high because it have to communicate with the Order entity
     */
    public boolean validateContainLetterAndNoEmpty(String name) {
        // Check name is not null
        if (name == null)
            return false;
        // Check if contain leter space only
        if (name.trim().length() == 0)
            return false;
        // Check if contain only leter and space
//        if (name.matches("^[a-zA-Z ]*$") == false)
//            return false;
        return true;
    }


    /**
     * This method calculates the shipping fees of order
     *
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(int amount) {
        Random rand = new Random();
        int fees = (int) (((rand.nextFloat() * 10) / 100) * amount);
        return fees;
    }
    public int calculateShippingFee(Order order) {
        int sum = 0;
        for (OrderMedia pd : order.getlstOrderMedia()) {
            if(pd.getMedia().getIsSupportedPlaceRushOrder()){
                sum += 10 * pd.getQuantity();
            }
        }
        return sum;
    }

    public boolean validateMediaPlaceRushorder(Order order){
        for (var pd : order.getlstOrderMedia()) {
            if (pd.getMedia().getIsSupportedPlaceRushOrder()) {
                return true;
            }
        }
        return false;
    }

//    /**
//<<<<<<< HEAD
//     * This method get product available place rush order media
//     *
//     * @param order
//     * @return media
//     * @throws SQLException
//     */
//    public Media getProductAvailablePlaceRush(Order order) throws SQLException {
//        Media media = new Media();
//        for (OrderMedia pd : order.getlstOrderMedia()) {
//            // CartMedia cartMedia = (CartMedia) object;
//            if( validateMediaPlaceRushorder()){
//                media = pd.getMedia();
//            }
//        }
//        return media;
//    }
//    /**
//=======
//>>>>>>> main
//     * @param province
//     * @param address
//     * @return boolean
//     */
    public boolean validateAddressPlaceRushOrder(String province, String address) {
        if (!validateContainLetterAndNoEmpty(address))
            return false;
        if (!province.equals("Thành phố Hà Nội"))
            return false;
        return true;
    }


 
}
