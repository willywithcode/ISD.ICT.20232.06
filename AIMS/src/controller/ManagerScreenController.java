package controller;

import java.sql.SQLException;
import java.util.List;

import entity.user.User;

public class ManagerScreenController extends BaseController{
    public void createUser(int id, String name, String email, String address, String phone, List<String> roles, String password) throws SQLException {
        User  user = new User();
        user.createUser(id, name, email,address,  phone, roles, password);
    }
    
    public void updateUser(int id, String name, String email, String address, String phone, List<String> roles) throws SQLException {
        User  user = new User();
        user.updateUser(id, name, email, address, phone, roles);
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
    
    public void getCurrentUser(String username) {
    	
    }
}
