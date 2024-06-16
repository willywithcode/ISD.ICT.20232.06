package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Utils {

    public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Logger LOGGER = getLogger(Utils.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-4s] [%1$tF %1$tT] [%2$-7s] %5$s %n");
    }


    /**
     * @param className
     * @return Logger
     */
    public static Logger getLogger(String className) {
        return Logger.getLogger(className);
    }


	public static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
    /**
     * @param num
     * @return String
     */
    public static String getCurrencyFormat(int num) {
        Locale vietname = new Locale("vi", "VN");
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(vietname);
        return defaultFormat.format(num * 1000);
    }

    /**
     * Return a {@link java.lang.String String} that represents the current time in the format of yyyy-MM-dd HH:mm:ss.
     *
     * @return the current time as {@link java.lang.String String}.
     */
    public static String getToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Return a {@link java.lang.String String} that represents the cipher text
     * encrypted by md5 algorithm.
     *
     * @param message - plain text as {@link java.lang.String String}.
     * @return cipher text as {@link java.lang.String String}.
     */
    public static String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            // converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Utils.getLogger(Utils.class.getName());
            digest = "";
        }
        return digest;
    }
    
    public enum CHECK {
        WRONG_ADDRESS,
        WRONG_PHONENUMBER,
        RIGHT_PHONENUMBER,
        WRONG_NAME,
        WRONG_EMAIL,
        RIGHT_EMAIL,
    }

    public static CHECK checkPhoneNumber(String phone) {
        if (phone.length() > 10 || phone.length() < 10 || phone.charAt(0) != '0')
            return CHECK.WRONG_PHONENUMBER;
        return CHECK.RIGHT_PHONENUMBER;
    }

    public static CHECK checkEmail (String email) {
        if (email.contains("@"))
            return  CHECK.RIGHT_EMAIL;
        return CHECK.WRONG_EMAIL;
    }
    
    public static boolean usernameExists(String username) throws SQLException{
        try {
            Statement stm = AIMSDB.getConnection().createStatement();
            String query = "SELECT * FROM User WHERE username = '" + username + "'";
            ResultSet res = stm.executeQuery(query);
            return res.next(); // If there is a next row, username exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of error or no results
        }
    }
    
    public static List<String> getProvincesList(){
    	List<String> provincesList = new ArrayList<>();
    	try {
    		Statement stm = AIMSDB.getConnection().createStatement();
			ResultSet res = stm.executeQuery("select full_name from provinces");
			while(res.next()) {
				String found_provinces = res.getString("full_name");
				provincesList.add(found_provinces);
			}
			
			Collections.sort(provincesList);
			
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return provincesList;
    }
    
    public static List<String> getDistrictsList(String provinceName){
    	List<String> districtsList = new ArrayList<>();
		try {
			Statement stm = AIMSDB.getConnection().createStatement();
			String query = "select "
					+ "districts.full_name AS district_full_name, "
					+ "provinces.code AS province_code, "
					+ "provinces.full_name AS province_full_name "
					+ "from districts, provinces "
					+ "where districts.province_code = provinces.code and provinces.full_name = '" + provinceName + "';";
			ResultSet res = stm.executeQuery(query);
			while(res.next()) {
				String found_district = res.getString("district_full_name");
				districtsList.add(found_district);
			}
			Collections.sort(districtsList);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
    	return districtsList;
    }
    
    public static List<String> getWardsList(String districtName){
    	List<String> wardsList = new ArrayList<>();
		try {
			Statement stm = AIMSDB.getConnection().createStatement();
			String query = "select "
					+ "wards.full_name as ward_full_name, "
					+ "districts.code as district_code, "
					+ "districts.full_name as district_full_name "
					+ "from wards, districts "
					+ "where wards.district_code = districts.code and districts.full_name = '" + districtName + "';";
			ResultSet res = stm.executeQuery(query);
			while(res.next()) {
				String found_district = res.getString("ward_full_name");
				wardsList.add(found_district);
			}
			Collections.sort(wardsList);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
    	return wardsList;
    }

}