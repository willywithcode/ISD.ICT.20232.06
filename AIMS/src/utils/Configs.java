package utils;

import java.time.format.DateTimeFormatter;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Configs {


    public static final String PROCESS_TRANSACTION_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_SecureHash = "HUQHTRVXVRGJJWHMBFCAUBAXOSAJBIND";
    public static final String vnp_TmnCode = "TXOOZNX4";

    // database Configs
    public static final String DB_NAME = "aims";
    public static final String DB_USERNAME = System.getenv("DB_USERNAME");
    public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    // static resource
    public static final String IMAGE_PATH = "assets/images";
    public static final String INVOICE_SCREEN_PATH = "/views/fxml/invoice.fxml";
    public static final String INVOICE_MEDIA_SCREEN_PATH = "/views/fxml/media_invoice.fxml";
    public static final String PAYMENT_SCREEN_PATH = "/views/fxml/payment.fxml";
    public static final String RESULT_SCREEN_PATH = "/views/fxml/result.fxml";
    public static final String SPLASH_SCREEN_PATH = "/views/fxml/splash.fxml";
    public static final String CART_SCREEN_PATH = "/views/fxml/cart.fxml";
    public static final String SHIPPING_SCREEN_PATH = "/views/fxml/shipping.fxml";
    public static final String CART_MEDIA_PATH = "/views/fxml/media_cart.fxml";
    public static final String HOME_PATH = "/views/fxml/home.fxml";
    public static final String HOME_MEDIA_PATH = "/views/fxml/media_home.fxml";
    public static final String POPUP_PATH = "/views/fxml/popup.fxml";
    public static final String DELIVERY_METHODS_PATH = "/views/fxml/delivery_methods_form.fxml";
    public static final String MANAGE_USER_SCREEN = "/views/fxml/manageUser.fxml";
    public static final String MANAGE_ORDER_SCREEN = "/views/fxml/manageOrder.fxml";
    public static final String ORDER_DETAILS_SCREEN = "/views/fxml/orderDetails.fxml";
    public static final int PLACE_RUSH_ORDER = 1;
    public static final int PLACE_ORDER = 2;
    public static final String VIEW_ORDER_SCREEN_PATH = "/views/fxml/view_order.fxml";
    public static String CURRENCY = "VND";
    
    public static final DateTimeFormatter formatter_date_time = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter formatter_date = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final String LOGIN_SCREEN_PATH = "/views/fxml/login.fxml";
    public static final String SIGN_UP_SCREEN_PATH = "/views/fxml/signup.fxml";
    public static final String MANAGER_MEDIA_SCREEN_PATH = "/views/fxml/crud_media_screen.fxml";
    public static final String MANAGER_SCREEN_PATH = "/views/fxml/manager.fxml";
    public static final String MEDIA_DETAIL_PATH = "/views/fxml/media_detail.fxml";
	public static float PERCENT_VAT = 10;
    public static Font REGULAR_FONT = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 24);
    public static String[] PROVINCES = {"Hà Nội", "Bắc Giang", "Bắc Kạn", "Cao Bằng", "Hà Giang", "Lạng Sơn", "Phú Thọ",
            "Quảng Ninh", "Thái Nguyên", "Tuyên Quang", "Yên Bái", "Điện Biên", "Hòa Bình", "Lai Châu", "Sơn La",
            "Bắc Ninh", "Hà Nam", "Hải Dương", "Hưng Yên", "Nam Định", "Ninh Bình", "Thái Bình", "Vĩnh Phúc", 
            "Hải Phòng", "Hà Tĩnh", "Nghệ An", "Quảng Bình", "Quảng Trị", "Thanh Hóa", "Thừa Thiên-Huế", "Đắk Lắk",
            "Đắk Nông", "Gia Lai", "Kon Tum", "Lâm Đồng", "Bình Định", "Bình Thuận", "Khánh Hòa", "Ninh Thuận",
            "Tây Ninh", "Hồ Chí Minh", "An Giang", "Bạc Liêu", "Bến Tre", "Cà Mau", "Đồng Tháp", "Hậu Giang",
            "Kiên Giang", "Long An", "Sóc Trăng", "Tiền Giang", "Trà Vinh", "Vĩnh Long", "Cần Thơ"};
}
