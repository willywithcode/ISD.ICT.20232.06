package views.screen.viewOrder;

import controller.ViewOrderController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewOrderScreenHandler extends BaseScreenHandler {

    @FXML
    private Label nameCustomer;

    @FXML
    private Text addressCustomer;

    @FXML
    private Label phoneCustomer;

    @FXML
    private Label emailCustomer;

    @FXML
    private Label statusOrder;

    @FXML
    private Label totalOrder;

    @FXML
    private Label shippingFees;

    @FXML
    private Label price;

    @FXML
    private TextField orderIDField;

    @FXML
    private Button backToHomeBtn;

    @FXML
    private Button getOrderBtn;


    public ViewOrderScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        this.setOrder("", "", "", "", "", "", "", "");
        this.backToHomeBtn.setOnMouseClicked(event -> {
            this.backToHomeScreen(event);
        });
        this.getOrderBtn.setOnMouseClicked(event -> {
            try {
                this.getOrder(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setOrder(String name, String address, String phone, String email, String status, String total, String shipping, String price) {
        nameCustomer.setText(name);
        addressCustomer.setText(address);
        phoneCustomer.setText(phone);
        emailCustomer.setText(email);
        statusOrder.setText(status);
        totalOrder.setText(total);
        shippingFees.setText(shipping);
        this.price.setText(price);
    }

    public void setOrder(String orderID) throws SQLException {
        ResultSet res = getBController().viewOrder(orderID);
        System.out.println(res.getString("name"));
        this.setOrder(res.getString("name"),
                res.getString("address"),
                res.getString("phone"),
                res.getString("email"),
                res.getString("status"),
                res.getInt("total_price") + ",000 vnd",
                res.getInt("shipping_fee") + ",000 vnd",
                res.getInt("price") + ",000 vnd");
    }

    @FXML
    void backToHomeScreen(MouseEvent event)  {
        this.homeScreenHandler.show();
    }

    @FXML
    void getOrder(MouseEvent event) throws SQLException {
        this.setOrder(orderIDField.getText());
    }

    @Override
    public ViewOrderController getBController() {
        return (ViewOrderController) super.getBController();
    }
}
