package views.screen.manager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.ManageMediaController;
import controller.ManageOrderController;
import controller.ManagerViewOrderController;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class ManageOrderScreenHandler extends BaseScreenHandler implements Initializable{

	
	@FXML
	private TableView<Order> orderTableView;
	
	@FXML
	private TableColumn<Order, String> orderId, shippingCode, name, email, phone, 
										province, district, ward, address, orderDate,
										instruction, type, status, shippingStatus;
	
	@FXML
	private TableColumn<Order, Integer> shippingFee, price, totalPrice;
	
	@FXML
	private Label totalOrders, pendingOrders, paidOrders;
	
	@FXML
	private Button viewOrder;
	
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
            countOrders();
            updateColorStatusColumn();
            updateColorShippingColumn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void showAllOrders() throws SQLException {
        List<Order> listOrders = getBController().getAllOrder();       
        orderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        shippingCode.setCellValueFactory(new PropertyValueFactory<>("genId"));
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
        shippingStatus.setCellValueFactory(new PropertyValueFactory<>("shippingStatus"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));   
        orderTableView.getItems().setAll(listOrders);      
        totalOrders.setText(String.valueOf(listOrders.size()));
    } 
	
	private void updateColorStatusColumn() {
		status.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
            @Override
            public TableCell<Order, String> call(TableColumn<Order, String> param) {
                return new TableCell<Order, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            switch (item.toLowerCase()) {
                                case "paid":
                                    setStyle("-fx-background-color: green; -fx-text-fill: white;");
                                    break;
                                case "pending":
                                    setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                                    break;
                                case "failed":
                                    setStyle("-fx-background-color: red; -fx-text-fill: white;");
                                    break;
                                default:
                                    setStyle("");
                                    break;
                            }
                        }
                    }
                };
            }
        });
	}
	
	private void updateColorShippingColumn() {
		shippingStatus.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
            @Override
            public TableCell<Order, String> call(TableColumn<Order, String> param) {
                return new TableCell<Order, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            switch (item.toLowerCase()) {
                                case "sending":
                                    setStyle("-fx-background-color: green; -fx-text-fill: white;");
                                    break;
                                case "packaging":
                                    setStyle("-fx-background-color: white; -fx-text-fill: black;");
                                    break;
                                case "payment failed":
                                    setStyle("-fx-background-color: red; -fx-text-fill: white;");
                                    break;
                                case "pending payment":
                                	setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                                    break;
                                default:
                                    setStyle("");
                                    break;
                            }
                        }
                    }
                };
            }
        });
	}
	
	@FXML
	private void viewBtnClicked() {
		Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
		if(selectedOrder != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(Configs.ORDER_DETAILS_SCREEN));
				Parent root = loader.load();
				
				// Get the controller instance from FXMLLoader
	            ManagerViewOrderDetailsHandler controller = loader.getController();
	            controller.setController(new ManagerViewOrderController());
	            controller.setParentController(this);
	            controller.setOrder(selectedOrder);

	            // Create a new stage for the popup
	            Stage popupStage = new Stage();
	            popupStage.setTitle("Order Details");

	            // Set the owner stage to the current window
	            popupStage.initOwner(viewOrder.getScene().getWindow());

	            // Set the scene for the popup stage
	            Scene scene = new Scene(root);
	            popupStage.setScene(scene);

	            // Pass the stage reference to the controller
	            controller.setStage(popupStage);

	            // Show the popup stage
	            popupStage.show();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void countOrders() throws SQLException {
		int totalPendingOrders = getBController().getCountOrderStatus("pending");
		int totalPaidOrders = getBController().getCountOrderStatus("Paid");
		pendingOrders.setText(String.valueOf(totalPendingOrders));
		paidOrders.setText(String.valueOf(totalPaidOrders));
		
	}

	public void refreshOrderTable() {
	    try {
	        showAllOrders();  // Method to fetch and display orders
	        countOrders();    // Method to update order counts
	        updateColorStatusColumn();    // Update color in status column
	        updateColorShippingColumn();  // Update color in shipping status column
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
