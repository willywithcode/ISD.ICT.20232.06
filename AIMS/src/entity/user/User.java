package entity.user;

import entity.db.AIMSDB;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private IntegerProperty id;
    private StringProperty username;
    private StringProperty email;
    private StringProperty address;
    private StringProperty phone;
    private BooleanProperty ban;
    private StringProperty password;
    private ListProperty<String> roles;
    protected Statement stm;

    public User(int id, String username, String email, String address, String phone, boolean ban, List<String> roles, String password) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.ban = new SimpleBooleanProperty(ban);
        this.roles = new SimpleListProperty<>(FXCollections.observableArrayList(roles));
        this.password = new SimpleStringProperty(password);
    }

    // JavaFX Property Getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty emailProperty() { return email; }
    public StringProperty addressProperty() { return address; }
    public StringProperty phoneProperty() { return phone; }
    public BooleanProperty banProperty() { return ban; }
    public StringProperty passwordProperty() { return password; }
    public ListProperty<String> rolesProperty() {return roles;}

    // Regular getters and setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public String getUsername() { return username.get(); }
    public void setUsername(String username) { this.username.set(username); }
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public String getAddress() { return address.get(); }
    public void setAddress(String address) { this.address.set(address); }
    public String getPhone() { return phone.get(); }
    public void setPhone(String phone) { this.phone.set(phone); }
    public boolean getBan() { return ban.get(); }
    public void setBan(boolean ban) { this.ban.set(ban); }
    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }
    public List<String> getRoles() { return roles.get(); }
    public void setRoles(List<String> roles) { this.roles.setAll(roles); } 

    public User() throws SQLException {
        stm = AIMSDB.getConnection().createStatement();
    }

    public User authenticate(String username, String password) throws SQLException {
        String sql = "select * from User " + "where username = '" + username + "' and password = '" + password + "'";
        System.out.println(sql);
        Statement stm = null;
        ResultSet res = null;
        try {
            stm = AIMSDB.getConnection().createStatement();
            res = stm.executeQuery(sql);
            System.out.println(res);
            if (res.next()) {
                int found_id = res.getInt("id");
                String found_username = res.getString("username");
                String found_email = res.getString("email");
                String found_address = res.getString("address");
                String found_phone = res.getString("phone");
                boolean found_ban = res.getBoolean("ban");
                String found_password = res.getString("password");
                List<String> roles = getUserRoles(found_id);

                User found_user = new User(found_id, found_username, found_email, found_address, found_phone, found_ban, roles, found_password);
                return found_user;
            } else {
                return null;
            }
        } finally {
            if (res != null) res.close();
            if (stm != null) stm.close();
        }
    }
    
    private List<String> getUserRoles(int userId) throws SQLException {
        String sql = "SELECT name FROM Roles r INNER JOIN UserRoles ur ON r.id = ur.role_id WHERE ur.user_id = " + userId;
        List<String> roles = new ArrayList<>();
        try {
        	Statement stm = AIMSDB.getConnection().createStatement();
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                roles.add(res.getString("name"));
            }
            res.close();
        } catch (SQLException e ) {
        	e.printStackTrace();
        }
        return roles;
    }

    public void createUser(int id, String username, String email, String address, String phone, List<String> roles, String password) {
        String insertSql = "insert into User (id, username, email, address, phone, ban, password) values ("
                + id + ", '" + username + "', '" + email + "', '" + address + "', '" + phone + "' , 0, '" + password + "')";
        System.out.println(insertSql);
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(insertSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for (String role : roles) {
        	int roleId = getRoleId(role);
        	String insertUserRoleSql = "insert into UserRoles (user_id, role_id) values ("
        			+ id + ", " + roleId + ")";
        	try {
        		Statement stm = AIMSDB.getConnection().createStatement();
        		stm.executeUpdate(insertUserRoleSql);
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
     }
    
    private int getRoleId(String role){
    	String queryRoleSql = "Select id from Roles where name = '" + role + "'";
    	System.out.println(queryRoleSql);
    	int role_id = -1;	
    	try {
    		Statement stm = AIMSDB.getConnection().createStatement();
    		ResultSet res = stm.executeQuery(queryRoleSql);
    		if(res.next()) {
    			role_id = res.getInt("id");
    		} 		
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return role_id;
    }

    public void updateUser(int id, String username, String email, String address, String phone, List<String> roles) {
        String updateUserSql = "UPDATE User SET " +
                "username = '" + username + "', " +
                "email = '" + email + "', " +
                "address = '" + address + "', " +
                "phone = '" + phone + "' " +
                "WHERE id = " + id;
        
        System.out.println(updateUserSql);
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(updateUserSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String deleteUserRoleSql = "delete from UserRoles where user_id = " + id;
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(deleteUserRoleSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for(String role : roles) {
        	int roleId = getRoleId(role);
        	String insertUserRolesSql = "insert into UserRoles (user_id, role_id) values ("
        			+ id + ", " + roleId + ")";
        	try {
        		Statement stm = AIMSDB.getConnection().createStatement();
        		stm.executeUpdate(insertUserRolesSql);
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }

    public void changePassword(int id, String password) {
        String updatePasswordSql = "UPDATE User SET " +
                "password = '" + password + "'" +
                "WHERE id = " + id;
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(updatePasswordSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String deleteUserSql = "DELETE FROM User WHERE id = " + id;
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(deleteUserSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void banUser(int id, boolean gt) {
        int result = !gt ? 1 : 0;
        String banUserSql = "UPDATE User SET " +
                "ban = " + result +
                " WHERE id = " + id;
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(banUserSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "{" +
                "  username='" + username + "'" +
                ", email='" + email + "'" +
                ", address='" + address + "'" +
                ", phone='" + phone + "'" +
                "}";
    }

    public List<User> getAllUser() throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from User");
        ArrayList<User> userList = new ArrayList<>();
        while (res.next()) {
            int found_id = res.getInt("id");
            String found_username = res.getString("username");
            String found_email = res.getString("email");
            String found_address = res.getString("address");
            String found_phone = res.getString("phone");
            boolean found_ban = res.getBoolean("ban");
            String found_password = res.getString("password");
            List<String> roles = getUserRoles(found_id);

            User found_user = new User(found_id, found_username, found_email, found_address, found_phone, found_ban, roles, found_password);
            userList.add(found_user);
        }
        return userList;
    }
}
