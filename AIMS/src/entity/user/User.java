package entity.user;

import entity.db.AIMSDB;
import javafx.beans.property.*;

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
    private StringProperty role;
    private StringProperty password;
    private StringProperty province;
    private StringProperty district;
    private StringProperty ward;
    protected Statement stm;

    public User(int id, String username, String email, String address, String phone, boolean ban, String role, String password, String province, String district, String ward) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.ban = new SimpleBooleanProperty(ban);
        this.role = new SimpleStringProperty(role);
        this.password = new SimpleStringProperty(password);
        this.province = new SimpleStringProperty(province);
        this.district = new SimpleStringProperty(district);
        this.ward = new SimpleStringProperty(ward);
    }

    // JavaFX Property Getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty emailProperty() { return email; }
    public StringProperty addressProperty() { return address; }
    public StringProperty phoneProperty() { return phone; }
    public BooleanProperty banProperty() { return ban; }
    public StringProperty roleProperty() { return role; }
    public StringProperty passwordProperty() { return password; }
    public StringProperty provinceProperty() { return province; }
    public StringProperty districtProperty() { return district; }
    public StringProperty wardProperty() { return ward; }

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
    public String getRole() { return role.get(); }
    public void setRole(String role) { this.role.set(role); }
    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }
    public String getProvince() { return province.get(); }
    public void setProvince(String province) { this.province.set(province); }
    public String getDistrict() { return district.get(); }
    public void setDistrict(String district) { this.district.set(district); }
    public String getWard() { return ward.get(); }
    public void setWard(String ward) { this.ward.set(ward); }

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
                String found_role = res.getString("role");
                String found_password = res.getString("password");
                String found_district = res.getString("district");
                String found_province = res.getString("province");
                String found_ward = res.getString("ward");

                User found_user = new User(found_id, found_username, found_email, found_address, found_phone, found_ban, found_role, found_password, found_province, found_district, found_ward);
                return found_user;
            } else {
                return null;
            }
        } finally {
            if (res != null) res.close();
            if (stm != null) stm.close();
        }
    }

    public void createUser(int id, String username, String email, String address, String phone, String role, String password, String province, String district, String ward) {
        String insertSql = "insert into User (id, username, email, address, phone, ban, role, password, province, district, ward) values ("
                + id + ", '" + username + "', '" + email + "', '" + address + "', '" + phone + "' , 0, '" + role + "', '" + password + "', '" + province + "', '" + district + "', '" + ward + "')";
        System.out.println(insertSql);
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(insertSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(int id, String username, String email, String address, String phone, String role, String province, String district, String ward) {
        String updateUserSql = "UPDATE User SET " +
                "username = '" + username + "', " +
                "email = '" + email + "', " +
                "address = '" + address + "', " +
                "phone = '" + phone + "', " +
                "role = '" + role + "', " +
                "province = '" + province + "', " +
                "district = '" + district + "', " +
                "ward = '" + ward + "', " +
                "WHERE id = " + id;
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            stm.executeUpdate(updateUserSql);
        } catch (Exception e) {
            e.printStackTrace();
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
                ", province='" + province + "'" +
                ", district='" + district + "'" +
                ", ward='" + ward + "'" +
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
            String found_role = res.getString("role");
            String found_password = res.getString("password");
            String found_district = res.getString("district");
            String found_province = res.getString("province");
            String found_ward = res.getString("ward");

            User found_user = new User(found_id, found_username, found_email, found_address, found_phone, found_ban, found_role, found_password, found_province, found_district, found_ward);
            userList.add(found_user);
        }
        return userList;
    }
}
