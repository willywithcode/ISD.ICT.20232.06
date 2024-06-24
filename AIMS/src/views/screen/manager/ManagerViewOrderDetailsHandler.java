package views.screen.manager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import controller.ManagerViewOrderController;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.Configs;

public class ManagerViewOrderDetailsHandler implements Initializable{

	private Order order;
	private ManagerViewOrderController controller;
	private ManageOrderScreenHandler parentController;
	private Stage stage;
	
	@FXML
	private Label name, phone, email, province, district, ward, address, instructions, deliveryTime, deliveryTimeLabel;
	
	@FXML
	private Label price, shippingFees, totalOrder;
	
	@FXML
	private ChoiceBox shippingStatus;
	
	@FXML
	private Button updateBtn, cancelBtn;
	
	@FXML
	private Label paymentStatus;
	
	@FXML
	private TableView listItems;
	
	@FXML
    private TableColumn<OrderMedia, Integer> quantity, total;

    @FXML
    private TableColumn<OrderMedia, String> mediaType, mediaTitle;

    @FXML
    private TableColumn<OrderMedia, Integer> mediaId, mediaPrice;
    
    @FXML
    private TableColumn<OrderMedia, Boolean> checkbox;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
		setupScreen();
		renderTable();
	}

	public ManagerViewOrderController getController() {
		return controller;
	}

	public void setController(ManagerViewOrderController controller) {
		this.controller = controller;
	}

	public ManageOrderScreenHandler getParentController() {
		return parentController;
	}

	public void setParentController(ManageOrderScreenHandler parentController) {
		this.parentController = parentController;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	private void setupScreen() {
		name.setText(this.order.getName());
		phone.setText(this.order.getPhone());
		email.setText(this.order.getEmail());
		province.setText(this.order.getProvince());
		district.setText(this.order.getDistrict());
		ward.setText(this.order.getWard());
		address.setText(this.order.getAddress());
		instructions.setText(this.order.getInstruction());
		
		if(this.order.getShippingType() == "rush shipping") {
			deliveryTime.setText(this.order.getOrderDate().format(Configs.formatter_date));
		}else {
			deliveryTimeLabel.setVisible(false);
			deliveryTime.setVisible(false);
		}
		
		price.setText(String.valueOf(this.order.getPrice()) + ".000 vnđ");
		shippingFees.setText(String.valueOf(this.order.getShippingFees()) + ".000 vnđ");
		totalOrder.setText(String.valueOf(this.order.getTotal_price()) + ".000 vnđ");
		paymentStatus.setText(this.order.getStatus());
		shippingStatus.setValue(this.order.getShippingStatus());
		
		// Set payment status text and apply background color based on payment status
        String status = this.order.getStatus();
        paymentStatus.setText(status);

        // Apply background color based on payment status
        if ("paid".equalsIgnoreCase(status)) {
            paymentStatus.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            shippingStatus.setItems(FXCollections.observableArrayList("packaging", "sending"));
        } else if ("pending".equalsIgnoreCase(status)) {
            paymentStatus.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
            shippingStatus.setItems(FXCollections.observableArrayList("pending payment"));
        } else if ("failed".equalsIgnoreCase(status)) {
            paymentStatus.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            shippingStatus.setItems(FXCollections.observableArrayList("payment fail"));
        } else {
            paymentStatus.setStyle(""); // Clear any previous styles
        }
	}
	
	private void renderTable() {
		List<OrderMedia> mediaList = getController().getOrderMediaById(this.order.getId());
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		total.setCellFactory(new Callback<TableColumn<OrderMedia, Integer>, TableCell<OrderMedia, Integer>>() {
	        @Override
	        public TableCell<OrderMedia, Integer> call(TableColumn<OrderMedia, Integer> param) {
	            return new TableCell<OrderMedia, Integer>() {
	                @Override
	                protected void updateItem(Integer item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty || getTableRow() == null) {
	                        setText(null);
	                    } else {
	                        OrderMedia orderMedia = getTableRow().getItem();
	                        if (orderMedia != null) {
	                            setText(String.format("%,d.000 vnđ", orderMedia.getPrice()));
	                        }
	                    }
	                }
	            };
	        }
	    });

        // Custom cell factories for nested Media properties
        mediaId.setCellFactory(new Callback<TableColumn<OrderMedia, Integer>, TableCell<OrderMedia, Integer>>() {
            @Override
            public TableCell<OrderMedia, Integer> call(TableColumn<OrderMedia, Integer> param) {
                return new TableCell<OrderMedia, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null) {
                            setText(null);
                        } else {
                            OrderMedia orderMedia = getTableRow().getItem();
                            if (orderMedia != null) {
                                setText(String.valueOf(orderMedia.getMedia().getId()));
                            }
                        }
                    }
                };
            }
        });

        mediaPrice.setCellFactory(new Callback<TableColumn<OrderMedia, Integer>, TableCell<OrderMedia, Integer>>() {
            @Override
            public TableCell<OrderMedia, Integer> call(TableColumn<OrderMedia, Integer> param) {
                return new TableCell<OrderMedia, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null) {
                            setText(null);
                        } else {
                            OrderMedia orderMedia = getTableRow().getItem();
                            if (orderMedia != null) {
                                setText(String.format("%,d.000 vnđ", orderMedia.getMedia().getPrice()));
                            }
                        }
                    }
                };
            }
        });

        mediaType.setCellFactory(new Callback<TableColumn<OrderMedia, String>, TableCell<OrderMedia, String>>() {
            @Override
            public TableCell<OrderMedia, String> call(TableColumn<OrderMedia, String> param) {
                return new TableCell<OrderMedia, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null) {
                            setText(null);
                        } else {
                            OrderMedia orderMedia = getTableRow().getItem();
                            if (orderMedia != null) {
                                setText(orderMedia.getMedia().getType());
                            }
                        }
                    }
                };
            }
        });

        mediaTitle.setCellFactory(new Callback<TableColumn<OrderMedia, String>, TableCell<OrderMedia, String>>() {
            @Override
            public TableCell<OrderMedia, String> call(TableColumn<OrderMedia, String> param) {
                return new TableCell<OrderMedia, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null) {
                            setText(null);
                        } else {
                            OrderMedia orderMedia = getTableRow().getItem();
                            if (orderMedia != null) {
                                setText(orderMedia.getMedia().getTitle());
                            }
                        }
                    }
                };
            }
        });

        // Convert mediaList to an ObservableList and set it to the TableView
        ObservableList<OrderMedia> observableMediaList = FXCollections.observableArrayList(mediaList);
        listItems.setItems(observableMediaList);
    }
	
	@FXML
	private void updateBtnClicked() throws SQLException {
		if(this.order != null) {
			getController().updateOrderShippingStatus(this.getOrder().getId(), (String) shippingStatus.getValue());
			getParentController().refreshOrderTable();
			stage.close();
		}
	}
	
}
