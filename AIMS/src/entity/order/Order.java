package entity.order;

import entity.db.AIMSDB;
import entity.media.Media;
import utils.Configs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order {

    private int id;
    private String genId;
    private int shippingFees, total_price, price, rush_shipping_fee = 0;
    private final String shippingId = this.generateRandomString(40);
    private List<OrderMedia> lstOrderMedia;
    private String name;
    private String province;
    private String district;
    private String ward;
    private String instruction;
    private String address;
    private String phone;
    private LocalDateTime orderDate;
    private String shippingType;
    private String status;
    private String email;
    private LocalDate deliveryTime;

    public String getShippingId(){
        return shippingId;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public List<OrderMedia> getLstOrderMedia() {
        return lstOrderMedia;
    }

    public void setLstOrderMedia(List<OrderMedia> lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order() {
        this.lstOrderMedia = new ArrayList<OrderMedia>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getShippingType() {
		return shippingType;
	}
    public int getRush_shipping_fee() {
        return rush_shipping_fee;
    }
    public void setRush_shipping_fee(int rush_shipping_fee) {
        this.rush_shipping_fee = rush_shipping_fee;
    }

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
    public int getTotal_price() {
        return total_price;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalDate deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getGenId() {
		return genId;
	}

	public void setGenId(String genId) {
		this.genId = genId;
	}

	public int getTotal_price() {
		return total_price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void createOrderEntity(){
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Order created");
        String query = "INSERT INTO 'Order' (name, province, address, phone, shipping_fee, district, ward, order_date, status, instruction, email, shipping_type, delivery_time, price, total_price) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, province);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, shippingFees);
            preparedStatement.setString(6, district);
            preparedStatement.setString(7, ward);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = orderDate.format(formatter);
            preparedStatement.setString(8, formattedDate);
            preparedStatement.setString(9, status);
            preparedStatement.setString(10, instruction);
            preparedStatement.setString(11, email);
            preparedStatement.setString(12, shippingType);
            
            if(deliveryTime == null) {
            	preparedStatement.setNull(13, java.sql.Types.VARCHAR);
            }else {
            	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            	String formattedDeliveryDate = deliveryTime.format(dateFormatter);
            	preparedStatement.setString(13, formattedDeliveryDate);
            }
            preparedStatement.setInt(14, getAmount());
            preparedStatement.setInt(15, total_price);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }
            
            System.out.println(preparedStatement);

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);

                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            this.insertOrderMedia();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void insertOrderMedia() {
        for (OrderMedia orderMedia : lstOrderMedia) {
            orderMedia.createOrderMediaEntity(id);
        }
    }


    /**
     * @param om
     */
    public void addOrderMedia(OrderMedia om) {
        this.lstOrderMedia.add(om);
    }


    /**
     * @param om
     */
    public void removeOrderMedia(OrderMedia om) {
        this.lstOrderMedia.remove(om);
    }


    /**
     * @return List
     */
    public List<OrderMedia> getlstOrderMedia() {
        return this.lstOrderMedia;
    }


    /**
     * @param lstOrderMedia
     */
    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    /**
     * @return int
     */
    public int getShippingFees() {
        return shippingFees;
    }

    /**
     * @param shippingFees
     */
    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    /**
     * @return int
     */
    public int getAmount() {
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) amount;
    }

    public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
    
    public List getAllOrder() throws SQLException{
    	Statement stm = AIMSDB.getConnection().createStatement();
    	ResultSet res = stm.executeQuery("select * from 'Order'");
    	ArrayList orders_list = new ArrayList<Order>();
    	while(res.next()) {
    		Order found_order = new Order();
    		found_order.setId(res.getInt("id"));
    		found_order.setGenId(res.getString("genID"));
    		found_order.setName(res.getString("name"));
    		found_order.setEmail(res.getString("email"));
    		found_order.setPhone(res.getString("phone"));
    		found_order.setProvince(res.getString("province"));
    		found_order.setDistrict(res.getString("district"));
    		found_order.setWard(res.getString("ward"));
    		found_order.setAddress(res.getString("address"));
    		
    		String orderDateString = res.getString("order_date");
    		if (orderDateString != null && !orderDateString.isEmpty()) {
                LocalDateTime orderDate = LocalDateTime.parse(orderDateString, Configs.formatter_date_time);
                found_order.setOrderDate(orderDate);
            }
    		String deliveryTimeString = res.getString("delivery_time");
    		if(deliveryTimeString != null && !deliveryTimeString.isEmpty()) {
    			LocalDate delivery_time = LocalDate.parse(deliveryTimeString, Configs.formatter_date);
    			found_order.setDeliveryTime(delivery_time);
    		}
    		found_order.setShippingType(res.getString("shipping_type"));
    		found_order.setShippingFees(res.getInt("shipping_fee"));
    		found_order.setInstruction(res.getString("instruction"));
    		found_order.setPrice(res.getInt("price"));
    		found_order.setTotal_price(res.getInt("total_price"));
    		found_order.setStatus(res.getString("status"));
    		
    		orders_list.add(found_order);
    	}
    	return orders_list;
    }
    
    public int getCountOrderStatus(String status) throws SQLException{
    	int count = 0;
    	Statement stm = AIMSDB.getConnection().createStatement();
    	ResultSet res = stm.executeQuery("select count(id) from 'Order' where status='" + status +"'");
    	if(res.next()) {
    		count = res.getInt("count(id)");
    	}
    	return count;
    }

}