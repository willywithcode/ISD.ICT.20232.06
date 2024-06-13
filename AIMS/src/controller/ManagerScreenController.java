package controller;

import java.sql.SQLException;
import java.util.List;

import entity.user.User;

public class ManagerScreenController extends BaseController{
	public List getAllUser() throws SQLException {
        return new User().getAllUser();
    }

    public void createUser(int id, String name,String email, String address, String phone, String role) throws SQLException {
        User  user = new User();
        user.createUser(id, name, email,address,  phone, role);
    }

    public void updateUser(int id, String name, String address, String email, String phone, String role) throws SQLException {
        User  user = new User();
        user.updateUser(id, name, address, email, phone, role);
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
    
    public boolean checkExistedUser(String username) throws SQLException {
    	User user = new User();
    	return user.checkExistedUser(username);
    }
}
