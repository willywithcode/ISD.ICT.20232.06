package entity.order;

import entity.db.AIMSDB;
import entity.media.Media;
import utils.Configs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {

    private int id;
    private int shippingFees;
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

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void createOrderEntity(){
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String query = "INSERT INTO 'Order' (name, province, address, phone, shipping_fee, district, ward, order_date, status, instruction) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
//            preparedStatement.setTimestamp(8, Timestamp.valueOf(orderDate));
            preparedStatement.setString(9, status);
            preparedStatement.setString(10, instruction);


            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);

                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            this.insertOrderMedia();
            try (PreparedStatement preparedStatement2 = AIMSDB.getConnection().prepareStatement(query)) {
                var sqlinsertShipment = "INSERT INTO Shipping ( shipType, deliveryInstruction, dateTime, deliverySub, orderId) " +
                        "VALUES ( ?, ?, ?, ?)";
                preparedStatement2.setInt(1, shipment.getShipType());
                preparedStatement2.setString(2, shipment.getDeliveryInstruction());
                preparedStatement2.setString(3, shipment.getDeliveryTime());
                preparedStatement2.setString(4, shipment.getShipmentDetail());
                preparedStatement2.setInt(5, id);
              preparedStatement2.executeUpdate();
            }
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
        return (int) (amount + (Configs.PERCENT_VAT / 100) * amount);
    }

}