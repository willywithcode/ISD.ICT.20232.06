package views.screen.payment;

import controller.PaymentController;
import entity.invoice.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import mailClient.MailService;
import mailClient.MailServiceImpl;
import utils.Configs;
import utils.VnPayConfig;
import views.screen.BaseScreenHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PaymentScreenHandler extends BaseScreenHandler {

    private Invoice invoice;
    @FXML
    private Label pageTitle;
    @FXML
    private VBox vBox;

    private MailService mailService;
    public PaymentScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;

        displayWebView();

    }
    
    /**
     * @param event
     */
    @FXML
    private void handleBack(MouseEvent event){
        // Back to previous screen
    	this.getPreviousScreen().show();
    }
    
    
    private void displayWebView(){
        var paymentController = new PaymentController();
        var paymentUrl = paymentController.getUrlPay(invoice.getOrder().getTotal_price(), "Thanh toan hoa don AIMS");
        WebView paymentView = new WebView();
        WebEngine webEngine = paymentView.getEngine();
        webEngine.load(paymentUrl);
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            // Xử lý khi URL thay đổi
            handleUrlChanged(newValue);
        });
        vBox.getChildren().clear();
        vBox.getChildren().add(paymentView);
    }

    // Hàm chuyển đổi query string thành Map
    private static Map<String, String> parseQueryString(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }

    /**
     * @param newValue url vnPay return về
     */
    private void handleUrlChanged(String newValue) {
        if (newValue.contains(VnPayConfig.vnp_ReturnUrl)) {
            try {
                URI uri = new URI(newValue);
                String query = uri.getQuery();

                // Chuyển đổi query thành Map
                Map<String, String> params = parseQueryString(query);

                payOrder(params);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param res kết quả vnPay trả về
     * @throws IOException
     */
    void payOrder(Map<String, String> res) throws IOException {

        var ctrl = (PaymentController) super.getBController();
        setMailService(new MailServiceImpl());
        Map<String, String> response = ctrl.makePayment(res, this.invoice.getOrder().getId(), this.invoice.getOrder().getShippingId(), mailService, this.invoice);

        BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH,
                response.get("RESULT"), response.get("MESSAGE"));
        ctrl.emptyCart();
        resultScreen.setPreviousScreen(this);
        resultScreen.setHomeScreenHandler(homeScreenHandler);
        resultScreen.setScreenTitle("Result Screen");
        resultScreen.show();

    }
    /**
     * @param mailService
     * Data coupling, control coupling because it is passing data to another class
     * This method is used to set the mail service
     * @param mailService
     * @return void
     * @SOLID Dependency inversion principle: PaymentController không phụ thuộc vào một lớp cụ thể, mà phụ thuộc vào một interface
     * @SRP This class is not violating the Single Responsibility Principle because it is responsible for managing the place order and it is not responsible for other tasks.
     */
    private void setMailService(MailServiceImpl mailService) {
        this.mailService = mailService;
    }
}