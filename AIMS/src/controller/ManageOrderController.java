package controller;

import java.sql.SQLException;
import java.util.List;
import entity.order.Order;

public class ManageOrderController extends BaseController{

	public List getAllOrder() throws SQLException{
		return new Order().getAllOrder();
	}
	
	public int getCountOrderStatus(String status) throws SQLException{
		return new Order().getCountOrderStatus(status);
	}
}
