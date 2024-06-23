package views.screen.manager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.ManageMediaController;
import controller.ManageOrderController;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;

public class ManageOrderScreenHandler extends BaseScreenHandler implements Initializable{

	
	@FXML
	private TableView<Order> orderTableView;
	
	@FXML
	private TableColumn<Order, String> orderGenId, name, email, phone, 
										province, district, ward, address, orderDate,
										instruction, type, status;
	
	@FXML
	private TableColumn<Order, Integer> shippingFee, price, totalPrice;
	
	@FXML
	private Label totalOrders;
	
	public ManageOrderScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}
	
	public ManageOrderController getBController() {
		return (ManageOrderController) super.getBController();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setBController(new ManageOrderController());
		try {
            showAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void showAllOrders() throws SQLException {
        List<Order> listOrder = getBController().getAllOrder();
        System.out.println(listOrder.size());
        
        orderGenId.setCellValueFactory(new PropertyValueFactory<>("genId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        province.setCellValueFactory(new PropertyValueFactory<>("province"));
        district.setCellValueFactory(new PropertyValueFactory<>("district"));
        ward.setCellValueFactory(new PropertyValueFactory<>("ward"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        instruction.setCellValueFactory(new PropertyValueFactory<>("instruction"));
        type.setCellValueFactory(new PropertyValueFactory<>("shippingType"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        shippingFee.setCellValueFactory(new PropertyValueFactory<>("shippingFees"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        
        orderTableView.getItems().setAll(listOrder);
        
        totalOrders.setText("" + listOrder.size());
    } 
	
	private void countOrders() {
		
	}

}
