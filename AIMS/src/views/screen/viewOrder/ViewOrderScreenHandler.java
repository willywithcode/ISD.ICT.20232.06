package views.screen.viewOrder;

import controller.ViewOrderController;
import entity.db.AIMSDB;
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
import java.sql.Statement;

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
    @FXML
    private Text textItem;


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
                try {
                    PopupScreen.error("Khoong tim thay don hang");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            } catch (IOException e) {
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

    public void setOrder(String orderID) throws SQLException, IOException {
        ResultSet res = getBController().viewOrder(orderID);
        if(res == null) {
            PopupScreen.error("Khong tim thay don hang");
            return;
        }
        System.out.println(res.getInt("id"));
        this.setOrder(res.getString("name"),
                res.getString("address"),
                res.getString("phone"),
                res.getString("email"),
                res.getString("status"),
                res.getInt("total_price") + ",000 vnd",
                res.getInt("shipping_fee") + ",000 vnd",
                res.getInt("price") + ",000 vnd");
        this.getDetailItem(res.getInt("id"));
    }
    private void getDetailItem(int id) throws SQLException {
//        String query = "SELECT quantity From OrderMedia WHERE orderID = " + id;
        String query = "SELECT OrderMedia.quantity,OrderMedia.price, Media.title  " +
                "FROM OrderMedia Join Media On OrderMedia.mediaID = Media.id WHERE OrderMedia.orderID = " + id;
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(query);
        while (res.next()) {
            textItem.setText(textItem.getText() + res.getString("title") + " -" + res.getInt("quantity") + " - " + res.getInt("price") + ",000 vnd\n");
        }
    }

    @FXML
    void backToHomeScreen(MouseEvent event)  {
        this.homeScreenHandler.show();
    }

    @FXML
    void getOrder(MouseEvent event) throws SQLException, IOException {
        this.setOrder(orderIDField.getText());
    }

    @Override
    public ViewOrderController getBController() {
        return (ViewOrderController) super.getBController();
    }
}
