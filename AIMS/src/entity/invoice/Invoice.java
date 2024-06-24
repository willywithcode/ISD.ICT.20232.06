package entity.invoice;

import entity.order.Order;

public class Invoice {

    private Order order;
    private int amount;

    public Invoice() {

    }

    public Invoice(Order order) {
        this.order = order;
    }


    /**
     * @return Order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @return int
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void saveInvoice() {

    }
    public String getDetailInvoice() {
        Order order = this.getOrder();
        String detail = "";
        for (int i = 0; i < order.getLstOrderMedia().size(); i++) {
            detail += order.getLstOrderMedia().get(i).getMedia().getTitle() +
                    " - " + order.getLstOrderMedia().get(i).getQuantity() +
                    " - " + order.getLstOrderMedia().get(i).getMedia().getPrice() + "k VND\n";
        }
        return
                "Mã đơn hàng:" + order.getShippingId() + "\n" +
                "Tên khách hàng: " + order.getName() + "\n" +
                "Địa chỉ: " + order.getAddress() + "\n" +
                "Số điện thoại: " + order.getPhone() + "\n" +
                "Tổng hóa đơn: " + this.getOrder().getTotal_price() + "k VND\n" +
                "Chi tiết: \n" + detail;
    }
}
