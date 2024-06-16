package controller;

import java.sql.SQLException;
import java.util.List;

import entity.user.User;

public class ManagerScreenController extends BaseController{
    public void createUser(int id, String name, String email, String address, String phone, String role, String password) throws SQLException {
        User  user = new User();
        user.createUser(id, name, email,address,  phone, role, password);
    }
    
    public void updateUser(int id, String name, String email, String address, String phone, String role) throws SQLException {
        User  user = new User();
        user.updateUser(id, name, email, address, phone, role);
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
