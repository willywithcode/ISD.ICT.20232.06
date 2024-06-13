package entity.user;

import entity.db.AIMSDB;
import entity.media.Media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class User {
	
	private int id;
    private String username;
    private String email;
    private String address;
    private String phone;
    private boolean ban;
    private String role;
    private String password;
    protected Statement stm;
    
    public User(int id, String username, String email, String address, String phone, boolean ban, String role, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.ban = ban;
        this.role = role;
        this.password = password;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean getBan() {
		return ban;
	}

	public void setBan(boolean ban) {
		this.ban = ban;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User() throws SQLException {
        stm = AIMSDB.getConnection().createStatement();
    }
    
    public User authenticate(String username, String password) throws SQLException{
    	String sql = "select * from User " + "where username = '" + username + "' and password = '" + password + "'";
    	System.out.println(sql);
    	Statement stm = null;
        ResultSet res = null;
        try {
        	stm = AIMSDB.getConnection().createStatement();
        	res = stm.executeQuery(sql);
        	System.out.println(res);
        	if(res.next()) {
        		int found_id = res.getInt("id");
        		String found_username = res.getString("username");
        		String found_email = res.getString("email");
        		String found_address = res.getString("address");
        		String found_phone = res.getString("phone");
        		boolean found_ban = res.getBoolean("ban");
        		String found_role = res.getString("role");
        		String found_password = res.getString("password");
        		
        		User found_user = new User(found_id, found_username, found_email, found_address, found_phone, found_ban, found_role, found_password);
        		return found_user;
        	}else {
        		return null;
        	}
        }finally {
        	if (res != null) res.close();
            if (stm != null) stm.close();
        }
    }
    
    public void createUser(int id, String name,String email, String address, String phone, String role) {
        String insertUserSql = "INSERT INTO \"user\" "+"(id, username, email, address, phone, ban, role, password)" +" VALUES (" + id + ", '" +
                name + "', '" + email + "', '" + address + "', '" + phone  + "', false, " + role +", '" + password + "'" + ")";
        System.out.println(insertUserSql);
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(insertUserSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(int id, String name, String email, String address, String phone, String role) {
        String updateUserSql = "UPDATE \"user\" SET " +
                "username = '" + name + "', " +
                "email = '" + email + "', " +
                "address = '" + address + "', " +
                "phone = '" + phone + "', " +
                "role = " + role + " "+
                "WHERE id = " + id;
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(updateUserSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(int id, String password) {
        String updatePasswordSql = "UPDATE \"user\" SET " +
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
        String deleteUserSql = "DELETE FROM \"user\" WHERE id = " + id;
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(deleteUserSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void banUser(int id, boolean gt) {
        int result = !gt ? 1: 0;
        String banUserSql = "UPDATE \"user\" SET " +
                "ban = " +  result +
                " WHERE id = " + id;
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(banUserSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @return String
     */
    // override toString method
    @Override
    public String toString() {
        return "{" +
                "  username='" + username + "'" +
                ", email='" + email + "'" +
                ", address='" + address + "'" +
                ", phone='" + phone + "'" +
                "}";
    }

    public List getAllUser() throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from \"user\"");
        ArrayList userList = new ArrayList<>();
        while (res.next()) {
        	int found_id = res.getInt("id");
    		String found_username = res.getString("username");
    		String found_email = res.getString("email");
    		String found_address = res.getString("address");
    		String found_phone = res.getString("phone");
    		boolean found_ban = res.getBoolean("ban");
    		String found_role = res.getString("role");
    		String found_password = res.getString("password");
    		
    		User found_user = new User(found_id, found_username, found_email, found_address, found_phone, found_ban, found_role, found_password);
//            LOGGER.info("Media" + media.quantity);
            userList.add(found_user);
        }
        
        return userList;
    }
    
    public boolean checkExistedUser(String username) {
    	String checkUser = "select count(*) from \"user\" where username='"+ username + "'";
    	try {
    		Statement stm = AIMSDB.getConnection().createStatement();
    		ResultSet res = stm.executeQuery(checkUser);
    		if(res.next()) {
    			return true;
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
}
