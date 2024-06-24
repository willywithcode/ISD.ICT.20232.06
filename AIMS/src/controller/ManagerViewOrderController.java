package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entity.order.Order;
import entity.order.OrderMedia;

public class ManagerViewOrderController extends BaseController{

	public List<OrderMedia> getOrderMediaById(int id) {
		return new OrderMedia().getOrderMediaList(id);
	}
	
	public void updateOrderShippingStatus(int orderId, String shippingStatus) throws SQLException {
		Order order = new Order();
		order.updateShippingStatus(orderId, shippingStatus);
	}
}
