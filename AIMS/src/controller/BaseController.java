package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import entity.user.User;

import java.sql.SQLException;
import java.util.List;


/**
 * This class is the base controller for our AIMS project.
 *
 */
public class BaseController {

    /**
     * The method checks whether the Media in Cart, if it were in, we will return
     * the CartMedia else return null.
     *
     * @param media media object
     * @return CartMedia or null
     */
    public CartMedia checkMediaInCart(Media media) {
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart.
     *
     * @return List[CartMedia]
     */
    public List getListCartMedia() {
        return Cart.getCart().getListMedia();
    }
    
    public List getAllUser() throws SQLException {
        return new User().getAllUser();
    }

    public void createUser(int id, String name, String email, String address, String phone, String role, String password, String province, String district, String ward) throws SQLException {
        User  user = new User();
        user.createUser(id, name, email,address,  phone, role, password, province, district, ward);
    }
    
    public void updateUser(int id, String name, String address, String email, String phone, String role, String province, String district, String ward) throws SQLException {
        User  user = new User();
        user.updateUser(id, name, email, address, phone, role, province, district, ward);
    }

    public void deleteUser(int id) throws SQLException {
        User user = new User();
        user.deleteUser(id);
    }

    public void banUser(int id, boolean gt) throws SQLException {
        User user = new User();
        user.banUser(id, gt);
    }

    public void changePassword(int id, String password) throws SQLException{
        User user = new User();
        user.changePassword(id, password);
    }
    
}
