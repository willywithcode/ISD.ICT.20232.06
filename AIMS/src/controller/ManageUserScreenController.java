package controller;

import java.sql.SQLException;
import java.util.List;

import entity.user.User;
/**
 * This class controls the flow of events in managing users
 * @SRP This class is not violating the Single Responsibility Principle because it is responsible for managing users and it is not responsible for other tasks.
 */
public class ManageUserScreenController extends BaseController{
    /**
     * This method gets all users
     * @return
     * @throws SQLException
     * Coupling is low because it only communicates with the User entity
     */
    public void createUser(String username, String fullname, String email, String address, String phone, List<String> roles, String password) throws SQLException {
        User  user = new User();
        user.createUser(username, fullname, email,address,  phone, roles, password);
    }
    
    public void updateUser(int id, String username, String fullname, String email, String address, String phone, List<String> roles) throws SQLException {
        User  user = new User();
        user.updateUser(id, username, fullname, email, address, phone, roles);
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
    
    public boolean checkUsernameExisted(String username) throws SQLException {
    	return User.usernameExists(username);
    }

}
